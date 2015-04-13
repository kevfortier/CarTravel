'''
Created on 2015-01-09

@author: Kevin
'''

from google.appengine.ext import ndb
    
class Utilisateur(ndb.Model):
    pseudo = ndb.StringProperty()
    courriel = ndb.StringProperty()
    password = ndb.StringProperty()
    
class Parcours(ndb.Model):
    idParcours = ndb.StringProperty()
    proprietaire = ndb.IntegerProperty()
    conducteur = ndb.IntegerProperty()
    jour = ndb.StringProperty()
    heure = ndb.StringProperty()
    repetitif = ndb.BooleanProperty()
    nbrPassagers = ndb.IntegerProperty()
    nbrPlacesDispo = ndb.IntegerProperty()
    nbrPlacesPrise = ndb.IntegerProperty()
    distSuppMax = ndb.FloatProperty()
    
    numCivDep = ndb.StringProperty()
    rueDep = ndb.StringProperty()
    villeDep = ndb.StringProperty()
    codePostalDep = ndb.StringProperty()
    
    numCivArr = ndb.StringProperty()
    rueArr = ndb.StringProperty()
    villeArr = ndb.StringProperty()
    codePostalArr = ndb.StringProperty()
    
class Notifications(ndb.Model):
    sender = ndb.StringProperty()
    receivers = ndb.StringProperty(repeated=True)
    message = ndb.StringProperty()
