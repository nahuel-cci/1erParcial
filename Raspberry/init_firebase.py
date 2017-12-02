################################################################
# Descripción
# Inicializa la BD con algunos nodos para poder 'jugar'
################################################################

import time
import pyrebase

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

time_hhmmss = time.strftime('%H:%M:%S')
time_hhmmss2 = time.strftime('%H:%M+5:%S')
date_mmddyyyy = time.strftime('%d/%m/%Y')


postData={
	"6bpDDxJ2sTMiQddDxJEyluOAtLx2": {
		"name": "nahuel",
		"devices": {"raspinano": True},
		"tasks":{
			"task1":{"state":"completed"},
			"task2":{"state":"onprogress"}
		}		
	}
}
results = users_ref.set(postData, user['idToken'])


devices_ref = db.child("devices")
postData = {
	"devices":{
		"raspinano": {
			"users": {
				"6bpDDxJ2sTMiQddDxJEyluOAtLx2": True				
			},
			"tasks": {
				"task1": True, #hace falta poner las tareas que no están asociadas? Lo más probable es que no
				"task2": True
			}			
		}		
	}
}
results = devices_ref.set(postData, user['idToken'])


tasks_ref = db.child("tasks")
postData={
	"task1": {
		"name": "Prender LED",
		"state": "onprogess",
		"daystart": date_mmddyyyy,
		"tstart": time_hhmmss,
		"duration": 10 #in minutes						
	},
	"task2": {
		"name": "Titilar LED",
		"frequency": 50,
		"state": "onprogess",
		"daystart": date_mmddyyyy,
		"tstart": time_hhmmss2,
		"duration": 10 #in minutes						
	}		
}
results = tasks_ref.set(postData, user['idToken'])



