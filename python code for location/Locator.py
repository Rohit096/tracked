from firebase import firebase
from datetime import datetime
import urllib

firebase = firebase.FirebaseApplication('https://track-b4be2.firebaseio.com/')

response = urllib.urlopen('https://ipinfo.io/loc').read()
my_ip = urllib.urlopen('https://ipinfo.io/ip').read()
lat,lon = response.split(',')

l = list(lon)
l.pop()
lon = ''.join(l)

p = list(my_ip) 
p.pop()
my_ip = ''.join(p)

t = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

result = firebase.put('User 1','Lat',float(lat))
result = firebase.put('User 1','Lon',float(lon))
result = firebase.put('User 1','Time',t)
result = firebase.put('User 1','ip',my_ip)
print (result)

