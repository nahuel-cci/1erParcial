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

# borro la base de datos
db.remove()

users_ref = db.child("users")
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
results = devices_ref.set(postData, user['idToken'])


pattern = '%d.%m.%Y %H:%M:%S'
date_time_task1 = '03.12.2017 11:00:02'
date_time_task2 = '03.12.2017 11:05:02'
epoch_task1 = int(time.mktime(time.strptime(date_time_task1, pattern)))
epoch_task2 = int(time.mktime(time.strptime(date_time_task2, pattern)))

scheduled_tasks_ref = db.child("scheduled_tasks")
postData={
	"task1": {
		"name": "on",		
		"creationtime": 1512306000,
		"executiontime": epoch_task1,
		"duration": 15 #in seconds						
	},
	"task2": {
		"name": "blink",
		"frequency": 50,
		"creationtime": 1512306300,
		"executiontime": epoch_task2,
		"duration": 15 #in seconds						
	}		
}
results = scheduled_tasks_ref.set(postData, user['idToken'])


tasks_ref = db.child("tasks")
postData = {
	"task1":{
		"state": "scheduled"
	},
	"task2":{
		"state": "scheduled"
	},
	"task3":{
		"state": "completed"
	}
}
results = tasks_ref.set(postData, user['idToken'])


