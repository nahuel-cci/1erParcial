# -*- coding: utf-8 -*-
################################################################
# main.py
#
# Se encarga de ejecutar las cola de tareas que se encuentran
# en la BDTR (Base de Datos de Tiempo Real).
#
# Busca la tarea scheduled más antigua.
# Actualiza el estado a "onprogress" y la ejecuta.
# Una vez finalizada, actualiza el estado a "completed".
################################################################

import pyrebase
import time

config = {
  "apiKey": "AIzaSyD84dnI8_Qw6Y1_2uJn10GLhCEq0Z3W7OU",
  "authDomain": "share-292a0.firebaseapp.com",
  "databaseURL": "https://share-292a0.firebaseio.com",
  "projectId": "share-292a0",
  "storageBucket": "share-292a0.appspot.com",
  #"serviceAccount": "C:\\Users\\nahuel\\Desktop\\Share-aa30013fd9f9.json" #Windows
  "serviceAccount": "Share-a0de70ff3bfd.json" #Linux	  
}
firebase = pyrebase.initialize_app(config)

# authenticate a user, which returns a token (inside user) to perfom authorized operations
auth = firebase.auth()
user = auth.sign_in_with_email_and_password("nahuel.cci@gmail.com", "firebase")

# get db reference
db = firebase.database()
users_ref = db.child("users")

