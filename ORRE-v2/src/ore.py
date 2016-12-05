#python side exposed script functions

import sys

from orre.scripting import ScriptAPI
from orre.scripting import GUIScriptHandler, AIScriptHandler

if not 'orre_registeredHandlers' in globals():
	global orre_registeredHandlers
	orre_registeredHandlers = {}

def on(eventType, **conditions):
	def decorator(func):
		global orre_registeredHandlers
		if not eventType in orre_registeredHandlers:
			orre_registeredHandlers[eventType] = []
		orre_registeredHandlers[eventType].append((func, conditions))
		return func
	return decorator
	
def orre_eventConditionsSatisfied(conditions, parameters):
	for condition in conditions:
		if condition in parameters:
			if parameters[condition] != conditions[condition]:
				return False
	return True

def dispatchScriptEvent(eventType, parameters):
	orre_handleEvent(eventType, parameters)

def dispatchEvent(eventType, parameters = {}):
	ScriptAPI.dispatchEvent(eventType, parameters)

def orre_handleEvent(eventType, parameters):
	global orre_registeredHandlers
	if eventType in orre_registeredHandlers:
		for registration in orre_registeredHandlers[eventType]:
			handler, conditions = registration
			if orre_eventConditionsSatisfied(conditions, parameters):
				handler(parameters)

def spawn(objectType):
	return ScriptAPI.spawn(objectType)

class GUI:
	def show(self, menu):
		GUIScriptHandler.show(menu)
	def hide(self, menu):
		GUIScriptHandler.hide(menu)
	def animateMenu(self, menu, animationName):
		GUIScriptHandler.animate(menu, animationName)
	def orre_notifyAnimationEventHandled(self, menu):
		GUIScriptHandler.notifyAnimationEndHandled(menu)

class AI:
	def registerTask(self, task):
		AIScriptHandler.registerTask(task)
	
@on('GUI_AnimationEventHandled')
def notifyAnimationEventHandled(eventParams):
	gui.orre_notifyAnimationEventHandled(eventParams['menuName'])
	
gui = GUI()
ai = AI()
