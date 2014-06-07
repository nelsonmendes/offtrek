import jinja2
import webapp2
import cgi
import datetime
import os
from base_handler import *

JINJA_ENVIRONMENT = jinja2.Environment(
    loader=jinja2.FileSystemLoader('templates'),
    extensions=['jinja2.ext.autoescape'],
    autoescape=True)

class Login(BaseHandler):

    def get(self):
        def putData(post):
                data = post.user.key().id()
                return post

    	if self.isLoggedIn():
    		message = self.request.get("message")
    		template_values = {
                "data" : posts
    		}

    		template = JINJA_ENVIRONMENT.get_template('xmldata.html')
    		self.response.write(template.render(template_values))