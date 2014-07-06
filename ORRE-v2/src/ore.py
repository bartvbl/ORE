#python side exposed script functions

import sys

from orre.scripting import ScriptAPI
from orre.scripting import GUIScriptHandler

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
	
def orre_handleEvent(eventType, parameters):
	global orre_registeredHandlers
	if eventType in orre_registeredHandlers:
		for registration in orre_registeredHandlers[eventType]:
			handler, conditions = registration
			if orre_eventConditionsSatisfied(conditions, parameters):
				handler(parameters)

def spawn(objectType):
	ScriptAPI.spawn(objectType)

class GUI:
	def show(self, menu):
		GUIScriptHandler.show(menu)
	def hide(self, menu):
		GUIScriptHandler.hide(menu)
	def animateMenu(self, menu, animationName):
		GUIScriptHandler.animate(menu, animationName)
	
gui = GUI()

