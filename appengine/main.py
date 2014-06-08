#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2
import os
import datetime
from pages import *
import api

config = {}
config['webapp2_extras.sessions'] = {
    'secret_key': 'j4KLrj43',
}

application = webapp2.WSGIApplication([
    ('/', Home),
    ('/login',Login),
    ('/post',PostPage),
    ('/register',Register),
    ('/register_handler',RegisterHandler),
    ('/login_handler', LoginHandler),
    ('/register_handler', RegisterHandler),
    ('/edit_profile_handler', EditProfileHandler),
    ('/login', Login),
    ('/register', Register),
    ('/logout', Logout),
    ('/session', Session),
    ('/feed', Feed),
    ('/profile', Profile),
    ('/show_profile', ShowProfile),
    ('/search', SearchResults),
    ('/post', PostPage),
    ('/api/login', api.Login),
    ('/api/newpost', api.NewPost),
    ('/api/postimage', api.PostImage),
    ('/api/delete_post', api.DeletePost),
    ('/api/delete_user',api.DeleteUser),
    ('/api/edit_post', api.EditPost),
    ('/api/get_post', api.GetPost),
    ('/api/like', api.Like),
    ('/api/receive_data', api.ReceiveData),
    ('/api/toggleFollow', api.Follow),
    ('/api/get_avatar', api.GetAvatar),
    ('/api/new_comment', api.NewComment),
    ('/api/delete_comment', api.DeleteComment),
    ('/api/session_data', api.SessionData),
    ('/api/ing_tags', api.IngTags),
    ('/api/register_verification', api.RegisterVerification),
    ('/api/verifyPassword', api.PasswordVerification)

], debug=True, config=config)