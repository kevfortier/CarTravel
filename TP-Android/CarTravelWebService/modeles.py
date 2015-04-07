'''
Created on 2015-01-09

@author: Kevin
'''

from google.appengine.ext import ndb
    
class Utilisateur(ndb.Model):
    pseudo = ndb.StringProperty()
    password = ndb.StringProperty()
    
class Parcours(ndb.Model):
    id = ndb.StringProperty()
    proprietaire = ndb.StringProperty()
    conducteur = ndb.StringProperty()
    
    
    
class Notifications(ndb.Model):
    sender = ndb.StringProperty()
    receivers = ndb.StringProperty(repeated=True)
    message = ndb.StringProperty()
