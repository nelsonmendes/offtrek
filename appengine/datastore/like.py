from google.appengine.ext import db
from user import User
from post import Post

# ----------------- CLASS LIKE -----------------
class Like(db.Model):	
	user = db.ReferenceProperty(User, required=True)
	post = db.ReferenceProperty(Post, required=True)

# ----------------- FUNCTIONS LIKE -----------------
def toogleLike(user_id, post_id):
	user_like = User.get_by_id(user_id)
	post_like = Post.get_by_id(post_id)
	if (not LikeDone(user_id, post_id)):
		like = Like(user=user_like, post=post_like)
		like.put()
		return True
	else:
		like_delete = Like.all()
		like_delete.filter("user =", user_like)
		like_delete.filter("post =", post_like)
		db.delete(like_delete.get())
		return False

def getPostLikes(post_id):
	post_like = Post.get_by_id(post_id)
	all_like = Like.all()
	all_like.filter("post =", post_like)
	return all_like.fetch(1000)

def deletePostLikes(post_id):
	like_posts = getPostLikes(post_id)
	if(like_posts is None):
		return True
	else:
		db.delete(like_posts)
		return True
	
def deleteLikesUser(user_id): 
	likes = Like.all().filter("user =", User.get_by_id(user_id))
	db.delete(likes)

def LikeDone(user_id, post_id):
	user_delete_like = User.get_by_id(user_id)
	post_delete_like = Post.get_by_id(post_id)
	like = Like.all()
	like.filter("user =", user_delete_like)
	like.filter("post =", post_delete_like)
	if (len(like.fetch(1000)) > 0):
		return True
	else:
		return False