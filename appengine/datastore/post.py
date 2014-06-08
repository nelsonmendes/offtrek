from google.appengine.ext import db
from google.appengine.api import images
from user import *
from datastore.user import getUserID
import re

STANDARD_WIDTH = 800

# ----------------- CLASS POST -----------------
class Post(db.Model):
	user = db.ReferenceProperty(User, required=True)
	title = db.StringProperty(required=True)
	photo = db.BlobProperty()
	rating = db.RatingProperty()
	description = db.TextProperty()
	tags = db.ListProperty(str)
	original_date = db.DateTimeProperty(auto_now_add=True)
	last_update_date = db.DateTimeProperty(auto_now=True)


# ----------------- FUNCTIONS POST -----------------
def addPost(user, title, photo):
	try:
		post = Post(user=user, title=title)
		post.put()
		return post.key().id()
		
	except TypeError, e:
		return None
		

def addTags(post_id, tags):
	post = Post.get_by_id(post_id)
	if (post != None and len(tags) > 0):
		post.tags.extend(tags)
		post.put()
		return True
	else:
		return False
	
def addDescription(post_id, description):
	post = Post.get_by_id(post_id)
	if (post != None):
		post.description = description
		post.put()
		return True
	else:
		return False

	
def addRating(post_id, rating):
	post = Post.get_by_id(post_id)
	if (post != None):
		post.rating = rating
		post.put()
		return True
	else:
		return False
	
def getPosts():
	post_query = Post.all()
	return post_query.fetch(1000)

def getPostByID(post_id):
	post = Post.get_by_id(post_id)
	return post

def getPostsByUser(user_id):
	user = User.get_by_id(user_id)
	post_query = Post.all()
	post_query.filter("user =", user)
	return post_query.fetch(1000)
	
def getPostsByUserFollowing(user_follower_id):
	user = User.get_by_id(user_follower_id)
	user_following_query = db.GqlQuery("SELECT user_following FROM Follow WHERE user_follower = :1", user)
	follow_list = user_following_query.fetch(1000)
	posts_list = []
	for follow in follow_list:
		u = searchUserByEmail(follow.user_following.email)
		user_following_posts = db.GqlQuery("SELECT * FROM Post WHERE user = :1", u)
		posts_list.extend(user_following_posts.fetch(1000))
	return posts_list
	
def deletePost(post_id):
	post_to_delete = Post.get_by_id(post_id)
	db.delete(post_to_delete)
	return True

def editPost(post_id, title, photo, description, tags):
	post_to_change = Post.get_by_id(post_id)
	if(post_to_change is not None):
		post_to_change.title = title
		if(photo != ''):
			image = images.Image(db.Blob(photo))
			image.resize(width=STANDARD_WIDTH)
			photo = image.execute_transforms(output_encoding=images.JPEG)
			size = len(photo)/1024
			if size < 1000 and size > 0: 
				post_to_change.photo = photo
		if(len(description) > 0):
			post_to_change.description = description
		post_to_change.tags = tags
		post_to_change.put()
		return True
	else:
		return False
	
def searchPosts(term):
	posts_query = getPosts()
	match_posts = []
	if posts_query:
		term = term.encode('utf-8').lower().strip()
		for post in posts_query:
			if re.search(term, post.title.encode('utf-8').lower().strip()):
				match_posts.append(post)
	return match_posts
