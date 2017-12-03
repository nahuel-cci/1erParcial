import pyrebase

def stream_handler(message):
    print('event={m[event]}; path={m[path]}; data={m[data]}'
        .format(m=message))



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


db = firebase.database()
my_stream =db.child('tasks').stream(stream_handler, user['idToken'] )

# Run Stream Handler forever
while True:
    data = input("[{}] Type exit to disconnect: ".format('?'))
    if data.strip().lower() == 'exit':
        print('Stop Stream Handler')
        if my_stream: my_stream.close()
        break
