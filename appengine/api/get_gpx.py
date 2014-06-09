import webapp2
from datastore import *
import json
import sys, traceback

# api/get_gpx?trek_id=

class GetGpx(webapp2.RequestHandler):
	
	def get(self):
		output = {}
		try:
			trek_id = self.request.get("trek_id")
			post = getPostByTrekId(trek_id)
			self.response.headers['Content-Type'] = 'text/xml'
			return self.response.out.write(post.xml)
		except:
			output["result"] = "error"
			output["stack"] = traceback.format_exc()
			self.response.out.write(json.dumps(output))