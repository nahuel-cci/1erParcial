# -*- coding: utf-8 -*-
################################################################
# main.py
#
# Se encarga de ejecutar la cola de tareas que se encuentran
# en la BDTR (Base de Datos de Tiempo Real).
#
# Busca la tarea scheduled más antigua.
# Actualiza el estado a "onprogress" y la ejecuta.
# Una vez finalizada, actualiza el estado a "completed".
################################################################

# Opcion1:
# nodo tareas


import pyrebase
import time
from multiprocessing import Process

IN_RASPY = False
if IN_RASPY == True:
	import RPi.GPIO as GPIO


#########################
# rpi
#########################
if IN_RASPY == True:
	GPIO.setmode(GPIO.BOARD)
	GPIO.setup(12, GPIO.OUT)
	p = GPIO.PWM(12, 0.5)
	
	

##########################
# funciones
#########################
def pwm():
	p = GPIO.PWM(12, 50)  # channel=12 frequency=50Hz
	p.start(0)
	while True:
		for dc in range(0, 101, 5):
			p.ChangeDutyCycle(dc)
			time.sleep(0.1)
		for dc in range(100, -1, -5):
			p.ChangeDutyCycle(dc)
			time.sleep(0.1)
           


#########################		
# firebase
#########################
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


#~ task = scheduled_ref.order_by_child("executiontime").limit_to_first(1).get(user['idToken'])
#~ print("\n",task.val(),"\n")
#~ task = db.child("scheduled_tasks").order_by_child("executiontime").limit_to_first(1).get(user['idToken'])
#~ print("\n",task.val(),"\n")
#########################
# aplicacion
#########################
while True:
	# Consulta a la BD por las tareas que no se realizaron todavía
	task = db.child("scheduled_tasks").order_by_child("executiontime").limit_to_first(1).get(user['idToken'])
	if task.val() is not None:
		# Obtengo los valores de la tarea
		ordered_dict = task.val()
		for x in ordered_dict:
			task_id = x
			print (task_id)
			duration = ordered_dict[x]['duration']
			executiontime = ordered_dict[x]['executiontime']
			action = ordered_dict[x]['name']
			
		### Alternativa para conseguir el task_id ###
		#l = list(ordered_dict)
		#task_id = l[0]
		#print (l[0])	
			
		
		# Compruebo el tiempo de ejecución
		# Compare local time vs execution time
				
		if IN_RASPY == True:
			if action == "pwm":
				process = Process(target=pwm)
			elif action == "blink":
				p = GPIO.PWM(12, 0.5)
				p.start(1)			
			elif action == "on":
				p = GPIO.output(12, 1)
			else:
				raise NameError('Acción Incorrecta')
			
		
		# Muevo el nodo de 'scheduled_tasks' a 'running_tasks'
		db.child("scheduled_tasks").child(task_id).remove()
		db.child("running_tasks").update(task.val(), user['idToken'])
			
		# Actualizo el estado en 'tasks'
		db.child("tasks").child(task_id).update({"state":"running"}, user['idToken'])	
			
		# v1 la más simple - se queda esperando hasta que termine el contador
		print ("Ejecutando: ", task_id)	
		time.sleep(duration)
		if IN_RASPY == True:
			if (action == "pwm"):
				process.terminate()
			p.stop()
			GPIO.cleanup()	
		
		# v2 - se corre otro proceso
		#t = Timer(ordered_dict["duracion"], timer_callback)
		#t.start() 
		
		# Muevo el nodo de 'running_tasks' a 'completed_tasks'
		db.child("running_tasks").child(task_id).remove()
		db.child("completed_tasks").update(task.val(), user['idToken'])
		
		# Actualizo el estado en 'tasks'
		db.child("tasks").child(task_id).update({"state":"completed"}, user['idToken'])	
	
	
