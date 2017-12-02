# Share - Aplicación en raspberry controlada por android

# Permite prender y apagar un led de manera remota mediante internet
# La raspi no tiene switch físico. En un futuro se puede agregar

# Inicialización
# Se prende la raspberry y, 
#	i) 	Se conecta y consulta si existe en la base de datos (BD).
#		i.a) Si existe, asigna a switch_state el mismo valor que tiene en la BD (True o False).
#		i.b) Si no existe, lo setea prendido por defecto y crea el nodo JSON en la BD.

# Concurrencia
# Un recurso, es decir un led de la raspi en este caso, puede ser utilizado por más de una persona.
# Lo que en consecuencia significa,
#	i) 	La escritura y lectura del recurso es asincrónica y debe ser atómica
#	ii) Protocolo de ejecución de solicitudes de distintos usuarios, opciones:
#		a) Prioridades a los usuarios
#		b) FIFO


# Quiero:
#	Apilar tareas. La lógica se mantiene igual aunque los usuarios sean disintos.
#	En la App quiero ver todas las tareas que se van a ejecutar y que se pueda ver en distintas formas
#		a) Tareas delante de mi
#		b) Todas las tareas, con algún tipo de distinción visual de las propias
#		c) Tarea en ejecución
#
# Por lo tanto, la lista de las tareas se almacena en el server. 
# La raspberry sólo conoce la tarea que está ejecutando (opción a) en "Situaciones"). No conoce la lista.

# Situaciones
# Se tienen tres tareas de duración X, Y y Z al momento de comenzar [Mismo usuario].
#
# a)La raspberry solo conoce la tarea que se está ejecutando 
#		Inicia la X
#		No hay conexión
#		Termina la X
#		Se queda esperando a que haya nuevamente conexión sin hacer nada. Recurso sin utilizar hasta que haya conexión nuevamente.
#
# b)La raspberry conoce todas las tareas. Antes de iniciar hace un "pull" de todas las tareas. (¿Puede llegar a demorar mucho tiempo el pedido de todas las tareas?)
#		-- Raspberry --							|	-- Android -- 
#		Inicia la X								|	Tarea X incializada
#		No hay conexión							|	Pérdida de conexión
#		Termina la X							|	---
#		Inicia la Y porque ya la tiene.			|	Usuario desea eliminar la Y, pero no puede porque la raspi está "sin conexión"
#		Termina la Y							|	---
#		Inicia la Z								|	---
#		Hay conexión							|	Tareas X e Y completadas. Pedido de eliminación de la tarea Y sin éxito.
#		Termina la Z							|	Tareas X, Y y Z completadas
#
# Se opta por la opción A por ser más sencilla y aunque un recurso puede quedar sin utilizarse cuando no hay conexión, se evita la ejecución de tareas que pueden
# llegar a ser canceladas posteriormente por el usuario.

# Tarea con cierto tiempo de ejecución
# La app de android setea el:
#	a) Tiempo de inicio 
#	b) Duración
# Se lo manda a la BD y a su vez corre el contador de forma local
# La raspy lo recibe y comienza la ejecución
# Cuando finaliza el timer de Android se muestra un cartel:
#	'Esperando confirmación'
# Se espera la interrupción asincrónica de la raspy-->server--> en Android.


# Estructura de la BD
# Cosas involucradas: usuarios, dispositivos, tareas
{
	"users":{
		 auth.uid: { #usuarioA
			"name": "nahuel"
			"devices":{
				"dev1": True
				"dev2": False
			},
			"tasks":{
				"task1":{"state":"completed"}
				"task2":{"state":"onprogress"}
			}
		}
		auth.uid: { #usuarioB
			"name": "kuku"
			"devices":{
				"dev1": True
				"dev2": False
			},
			"tasks":{
				"task1":{"state":"completed"}
				"task2":{"state":"onprogress"}
			}
		}
		...
	}
		
	"devices":{
		"raspinano": {
			"users": {
				auth.uid: True, #usuarioA
				auth.uid: False #usuarioB
			},
			"tasks": {
				task1: True, #hace falta poner las tareas que no están asociadas? Lo más probable es que no
				task3: True
			}			
		}
		...		
	}
	
	"tasks": {
		"task1": {
			"name": "Prender LED",
			"state": "onprogess",
			"daystart": 05/12/2017,
			"tstart": 14:00:00,
			"duration": 10 #in minutes						
		}
		
	}
			
		dispositivos (ie, raspy)
		tareas

	dispositivos:
		tareas






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

auth = firebase.auth()
#authenticate a user
user = auth.sign_in_with_email_and_password("nahuel.cci@gmail.com", "firebase")


#Create node
db = firebase.database()

switch_state = True
time_hhmmss = time.strftime('%H:%M:%S')
date_mmddyyyy = time.strftime('%d/%m/%Y')

postData = {	
				"devices": {
					"name": "raspberry",
					"date": date_mmddyyyy,
					"time": time_hhmmss,
					"users": {
						"nahuel": True,
						"kuku": False
					},
					"switch": switch_state
				}
			}
#db.push(state, user['idToken'])


#Get a key for a new Post.
newPostKey = db.ref().child('posts').push().key;

#Write the new post's data simultaneously in the posts list and the user's post list.
updates = {}
updates['/posts/' + newPostKey] = postData
updates['/user-posts/' + uid + '/' + newPostKey] = postData

firebase.database().ref().update(updates);

