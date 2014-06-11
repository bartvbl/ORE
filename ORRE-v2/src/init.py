#python side exposed script functions

from orre.scripting import ScriptAPI

orre_registeredHandlers = {}

def on(eventType, handler):
	if not eventType in orre_registeredHandlers:
		orre_registeredHandlers[eventType] = []
	orre_registeredHandlers[eventType].append(handler)
	
def orre_handleEvent(eventType, payload):
	if eventType in orre_registeredHandlers:
		for handler in orre_registeredHandlers[eventType]:
			handler(payload)

def spawn(objectType):
	ScriptAPI.spawn(objectType)

def createTextButton(x, y, text, handler):
	pass

def createImageButton(x, y, textureResourceName, handler):
	pass

