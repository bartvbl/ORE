#python side exposed script functions

from orre.scripting import ScriptAPI
from orre.scripting import GUIScriptHandler

orre_registeredHandlers = {}

def on(eventType):
	def decorator(func):
		if not eventType in orre_registeredHandlers:
			orre_registeredHandlers[eventType] = []
		orre_registeredHandlers[eventType].append(func)
		return func
	return decorator	
	
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

class GUI:
	def show(self, menu):
		GUIScriptHandler.show(menu)
	def hide(self, menu):
		GUIScriptHandler.hide(menu)
	def animateMenu(self, menu, animationName):
		GUIScriptHandler.animate(menu, animationName)
	
gui = GUI()