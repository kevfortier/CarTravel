'''
Created on 2015-01-09

@author: Kevin
'''
# Source : https://cloud.google.com/appengine/docs/python/ndb/

from google.appengine.ext import ndb
from google.appengine.ext import db

import json
import webapp2
import logging
import datetime

from modeles import Utilisateur, Notifications
from symbol import if_stmt

def serialiser_pour_json(obj):
    ''' Retourne une fonction pour mettre un objet en JSON'''
    
    if (isinstance(obj, datetime.date) or isinstance(obj, datetime.datetime)):
        # Pour une date, on retourne la String du format ISO
        return obj.isoformat()  
    else:
        return obj
    
def ont_en_contact(utilisateur):
    contacts = Utilisateur.query(utilisateur == Utilisateur.contacts)
    noms = []
    for contact in contacts:
        noms.append(contact.key.id())
    return noms

def ajouterNotification(sender, message):
    notification = Notifications()
    notification.sender = sender
    notification.receivers = ont_en_contact(sender)
    notification.message = message
    notification.put()

    
class MainPageHandler(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
        self.response.out.write('<html><body><h1>Demo Google App Engine fonctionne bien !</h1></body></html>')
        
 
#
#    Classe pour gerer les utilisateurs
#    get sans username   retourne tous les utilisateurs, possibilite de faire une recherche avec le pseudo
#    get avec username   retourne l'utilisateur choisi
#    put avec username inexistant   cree l'usager
#    put avec username existant   modifie l'usager si le mot de passe est bon
#    delete sans username   supprime tous les utilisateurs
#    delete avec username   supprime l'utilisateur si le mot de passe est bon
class UtilisateurHandler(webapp2.RequestHandler):
    def get(self, username = None):
        try:
            resultat = None
            
            if(username is None):
            # Lister tous les utilisateurs par ordre alphabetique de pseudo, selon le filtre (si applique)
                resultat = []
                query = Utilisateur.query()
                
                #filtre = self.request.get('filtre')
                #if(filtre != ''):
                #    query = query.filter(filtre in Utilisateur.pseudo)
                #query = query.order(Utilisateur.pseudo)
                
                query = query.order(Utilisateur.pseudo)
                
                for u in query:
                    dictUtilisateur = {}
                    dictUtilisateur['pseudo'] = u.pseudo
                    dictUtilisateur['contacts'] = u.contacts
                    dictUtilisateur['username'] = u.key.id()
                    resultat.append(dictUtilisateur)
            else:
            # Retourner l'utilisateur demande
                cle = ndb.Key('Utilisateur', username)
                utilisateur = cle.get()
                if(utilisateur is None):
                    self.response.set_status(404)
                    return
                resultat = {}
                resultat['pseudo'] = utilisateur.pseudo
                resultat['contacts'] = utilisateur.contacts
            
            self.response.headers['Content-Type'] = 'application/json'
            self.response.out.write(json.dumps(resultat))
            
        except (ValueError, db.BadValueError), ex:
            logging.info(ex)
            self.error(400)
        
        except Exception, ex:
            logging.exception(ex)
            self.error(500)
    
    def put(self, username):
        try:
            cle = ndb.Key('Utilisateur', username)
            utilisateur = cle.get()
            jsonObj = json.loads(self.request.body)
            status = 204
            if(utilisateur is None):
                #Nouvel utilisateur
                utilisateur = Utilisateur(key=cle)
                logging.debug(utilisateur)
                utilisateur.pseudo = jsonObj['pseudo']
                utilisateur.password = jsonObj['password']
                utilisateur.put()
                status = 201
            elif(utilisateur.password == jsonObj['password'] and self.request.get('modif') == 'modif'):
                #Modification de l'utilisateur selon ce qui est fourni
                if(jsonObj['pseudo'] is not None):
                    utilisateur.pseudo = jsonObj['pseudo']
                if(jsonObj['contacts'] is not None):
                    utilisateur.contacts = jsonObj['contacts'].replace("[","").replace("]","").split(", ")
                utilisateur.put()
                status = 201
            self.response.set_status(status)
            
        except (ValueError, db.BadValueError, KeyError), ex:
            logging.info(ex)
            self.error(400)
        
        except Exception, ex:
            logging.exception(ex)
            self.error(500)
            
    def delete(self, username = None):
        try:
            if(username is None):
                ndb.delete_multi(Utilisateur.query().fetch(keys_only=True))
                self.response.set_status(204)
            else:
                cle = ndb.Key('Utilisateur', username)
                if (cle.get() is not None):
                    jsonObj = json.loads(self.request.body)
                    if(cle.get().password == jsonObj['password']):
                        cle.delete()
                        self.response.set_status(204) 
                else:
                    self.error(404)
        
        except Exception, ex:
            logging.exception(ex)
            self.error(500)
            
class ParcoursHandler(webapp2.RequestHandler):
    def get(self, username = None):
    def put(self, username):
    def delete(self, username = None):
        
            
class Connexion(webapp2.RequestHandler):
    def get(self,username):
        try:
            resultat = None
            cle = ndb.Key('Utilisateur', username)
            utilisateur = cle.get()
            if(utilisateur is None):
                self.response.set_status(404)
                return
            
            if(utilisateur.password == self.request.get('password')):
                utilisateur.isConnected = True
                utilisateur.put();
                resultat = utilisateur.to_dict()
            
            self.response.headers['Content-Type'] = 'application/json'
            self.response.out.write(json.dumps(resultat))
        
        except (ValueError, db.BadValueError, KeyError), ex:
            logging.info(ex)
            self.error(400)
        except Exception, ex:
            logging.exception(ex)
            self.error(500)
            
class ObtenirNotifications(webapp2.RequestHandler):
    def get(self, username):
        try:
            if(username is not None):
                cle = ndb.Key('Utilisateur', username)
                utilisateur = cle.get()
                if(utilisateur is None):
                    self.response.set_status(404)
                    return
                resultat = []
                if(utilisateur.password == self.request.get('password')):
                    notifs = Notifications.query(username == Notifications.receivers)
                    for notif in notifs:
                        jsonNotif = {}
                        jsonNotif['message'] = notif.message
                        jsonNotif['sender'] = notif.sender
                        resultat.append(jsonNotif)
                        
                        notif.receivers.remove(username)
                        if(len(notif.receivers) == 0):
                            notif.key.delete()
                        else:
                            notif.put()
                        
                
                self.response.headers['Content-Type'] = 'application/json'
                self.response.out.write(json.dumps(resultat))
                 
        except (ValueError, db.BadValueError, KeyError), ex:
            logging.info(ex)
            self.error(400)
        
        except Exception, ex:
            logging.exception(ex)
            self.error(500)


application = webapp2.WSGIApplication(
    [
        ('/',   MainPageHandler),
        webapp2.Route(r'/utilisateurs',             handler=UtilisateurHandler, methods=['GET', 'DELETE']),
        webapp2.Route(r'/utilisateurs/<username>',  handler=UtilisateurHandler, methods=['GET', 'PUT',  'DELETE']),
        webapp2.Route(r'/utilisateurs/<username>/connexion', handler=Connexion, methods=['GET']),
        webapp2.Route(r'/utilisateurs/<username>/notifications', handler=ObtenirNotifications, methods=['GET'])
        
    ],
    debug=True)