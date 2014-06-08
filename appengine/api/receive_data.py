import webapp2
from datastore import *
from google.appengine.ext import db
from pages import BaseHandler
import json

class ReceiveData(BaseHandler):

	def post(self):
		title = self.request.get("title")
		photo = ""
		xml = self.request.get("gpx")
		email = self.request.get("email")
		user = searchUserByEmail(email)
		postId = addPost(user, title, photo, xml)
		"""if postId is not None:
			addIngredients(postId, ingredients)
			for ing in ingredients:
				addIngredient(ing,None)
			if len(recipe) > 0:
				addRecipe(postId,recipe)"""
		output = {}
		output["result"] = "ok"
		return self.response.out.write(json.dumps(output))