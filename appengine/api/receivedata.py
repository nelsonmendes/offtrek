import webapp2
from datastore import *
from google.appengine.ext import db
from pages import BaseHandler
from xml.dom import minidom


class ReceiveData(BaseHandler):

	def xmldata(self):
		xmldoc = minidom.parse('track_gpx.xml')
		itemlist = xmldoc.getElementsByTagName('trkpt') 
		print len(itemlist)
		print itemlist[0].attributes['lon'].value
		for s in itemlist :
		    print s.attributes['lon'].value