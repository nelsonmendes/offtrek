import webapp2
from datastore import *
from google.appengine.ext import db
from pages import BaseHandler
import json

class Like(BaseHandler):

	def get(self):
		output = {}
		try:
			post_id = int(self.request.get("postId"))
			user_id = int(self.get_session_user_id())
			self.response.headers['Content-Type'] = 'application/json'
			toogleLike(user_id, post_id)
			output["answer"] = "done"
			output["result"] = "ok"
			self.response.out.write(json.dumps(output))
		except:
			output["result"] = "error"
			self.response.out.write(json.dumps(output))