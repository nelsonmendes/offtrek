import webapp2
from datastore import *
from google.appengine.ext import db
from pages import BaseHandler
import sys, traceback
import json

class ReceiveData(BaseHandler):

	def post(self):
		output = {}
		try:
			title = self.request.get("title")
			photo = ""
			xml = self.request.get("gpx")
			trek_id = self.request.get("trek_id")
			email = self.request.get("email")
			user = searchUserByEmail(email)
			addPost(user, title, photo, xml, trek_id)
		except:
			output["result"] = "fail"
			output["stack"] = traceback.format_exc()
			return self.response.out.write(json.dumps(output))
		finally:
			output["result"] = "ok"
			return self.response.out.write(json.dumps(output))		