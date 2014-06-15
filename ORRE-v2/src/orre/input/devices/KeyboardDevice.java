package orre.input.devices;

import org.lwjgl.input.Keyboard;

import orre.input.CommandDispatcher;
import orre.input.InputEvent;
import orre.input.KeyType;

public class KeyboardDevice {
	private final CommandDispatcher dispatcher;
	
	public KeyboardDevice(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void update() {
		while(Keyboard.next()) {
			final int pressedKey = Keyboard.getEventKey();
			final boolean isKeyDown = Keyboard.getEventKeyState();
			
			final double keyDelta = isKeyDown ? 1.0 : -1.0;
			final double keyValue = isKeyDown ? 1.0 : 0.0;
			
			KeyType type = getKeyType(pressedKey);
			System.out.println("Keyboard: " + type);
			
			if(type != null) {
				dispatcher.dispatchCommand(type, keyValue, keyDelta);
			}
		}
	}

	private KeyType getKeyType(int pressedKey) {
		switch(pressedKey) {
		case Keyboard.KEY_0:
			return KeyType.KEY_0;
		case Keyboard.KEY_1:
			return KeyType.KEY_1;
		case Keyboard.KEY_2:
			return KeyType.KEY_2;
		case Keyboard.KEY_3:
			return KeyType.KEY_3;
		case Keyboard.KEY_4:
			return KeyType.KEY_4;
		case Keyboard.KEY_5:
			return KeyType.KEY_5;
		case Keyboard.KEY_6:
			return KeyType.KEY_6;
		case Keyboard.KEY_7:
			return KeyType.KEY_7;
		case Keyboard.KEY_8:
			return KeyType.KEY_8;
		case Keyboard.KEY_9:
			return KeyType.KEY_9;
		case Keyboard.KEY_A:
			return KeyType.KEY_A;
		case Keyboard.KEY_ADD:
			return KeyType.KEY_ADD;
		case Keyboard.KEY_APOSTROPHE:
			return KeyType.KEY_APOSTROPHE;
		case Keyboard.KEY_APPS:
			return KeyType.KEY_APPS;
		case Keyboard.KEY_AT:
			return KeyType.KEY_AT;
		case Keyboard.KEY_AX:
			return KeyType.KEY_AX;
		case Keyboard.KEY_B:
			return KeyType.KEY_B;
		case Keyboard.KEY_BACK:
			return KeyType.KEY_BACK;
		case Keyboard.KEY_BACKSLASH:
			return KeyType.KEY_BACKSLASH;
		case Keyboard.KEY_C:
			return KeyType.KEY_C;
		case Keyboard.KEY_CAPITAL:
			return KeyType.KEY_CAPITAL;
		case Keyboard.KEY_CIRCUMFLEX:
			return KeyType.KEY_CIRCUMFLEX;
		case Keyboard.KEY_CLEAR:
			return KeyType.KEY_CLEAR;
		case Keyboard.KEY_COLON:
			return KeyType.KEY_COLON;
		case Keyboard.KEY_COMMA:
			return KeyType.KEY_COMMA;
		case Keyboard.KEY_CONVERT:
			return KeyType.KEY_CONVERT;
		case Keyboard.KEY_D:
			return KeyType.KEY_D;
		case Keyboard.KEY_DECIMAL:
			return KeyType.KEY_DECIMAL;
		case Keyboard.KEY_DELETE:
			return KeyType.KEY_DELETE;
		case Keyboard.KEY_DIVIDE:
			return KeyType.KEY_DIVIDE;
		case Keyboard.KEY_DOWN:
			return KeyType.KEY_DOWN;
		case Keyboard.KEY_E:
			return KeyType.KEY_E;
		case Keyboard.KEY_END:
			return KeyType.KEY_END;
		case Keyboard.KEY_EQUALS:
			return KeyType.KEY_EQUALS;
		case Keyboard.KEY_ESCAPE:
			return KeyType.KEY_ESCAPE;
		case Keyboard.KEY_F:
			return KeyType.KEY_F;
		case Keyboard.KEY_F1:
			return KeyType.KEY_F1;
		case Keyboard.KEY_F10:
			return KeyType.KEY_F10;
		case Keyboard.KEY_F11:
			return KeyType.KEY_F11;
		case Keyboard.KEY_F12:
			return KeyType.KEY_F12;
		case Keyboard.KEY_F13:
			return KeyType.KEY_F13;
		case Keyboard.KEY_F14:
			return KeyType.KEY_F14;
		case Keyboard.KEY_F15:
			return KeyType.KEY_F15;
		case Keyboard.KEY_F16:
			return KeyType.KEY_F16;
		case Keyboard.KEY_F17:
			return KeyType.KEY_F17;
		case Keyboard.KEY_F18:
			return KeyType.KEY_F18;
		case Keyboard.KEY_F19:
			return KeyType.KEY_F19;
		case Keyboard.KEY_F2:
			return KeyType.KEY_F2;
		case Keyboard.KEY_F3:
			return KeyType.KEY_F3;
		case Keyboard.KEY_F4:
			return KeyType.KEY_F4;
		case Keyboard.KEY_F5:
			return KeyType.KEY_F5;
		case Keyboard.KEY_F6:
			return KeyType.KEY_F6;
		case Keyboard.KEY_F7:
			return KeyType.KEY_F7;
		case Keyboard.KEY_F8:
			return KeyType.KEY_F8;
		case Keyboard.KEY_F9:
			return KeyType.KEY_F9;
		case Keyboard.KEY_FUNCTION:
			return KeyType.KEY_FUNCTION;
		case Keyboard.KEY_G:
			return KeyType.KEY_G;
		case Keyboard.KEY_GRAVE:
			return KeyType.KEY_GRAVE;
		case Keyboard.KEY_H:
			return KeyType.KEY_H;
		case Keyboard.KEY_HOME:
			return KeyType.KEY_HOME;
		case Keyboard.KEY_I:
			return KeyType.KEY_I;
		case Keyboard.KEY_INSERT:
			return KeyType.KEY_INSERT;
		case Keyboard.KEY_J:
			return KeyType.KEY_J;
		case Keyboard.KEY_K:
			return KeyType.KEY_K;
		case Keyboard.KEY_KANA:
			return KeyType.KEY_KANA;
		case Keyboard.KEY_KANJI:
			return KeyType.KEY_KANJI;
		case Keyboard.KEY_L:
			return KeyType.KEY_L;
		case Keyboard.KEY_LBRACKET:
			return KeyType.KEY_LBRACKET;
		case Keyboard.KEY_LCONTROL:
			return KeyType.KEY_LCONTROL;
		case Keyboard.KEY_LEFT:
			return KeyType.KEY_LEFT;
		case Keyboard.KEY_LMENU:
			return KeyType.KEY_LMENU;
		case Keyboard.KEY_LMETA:
			return KeyType.KEY_LMETA;
		case Keyboard.KEY_LSHIFT:
			return KeyType.KEY_LSHIFT;
		case Keyboard.KEY_M:
			return KeyType.KEY_M;
		case Keyboard.KEY_MINUS:
			return KeyType.KEY_MINUS;
		case Keyboard.KEY_MULTIPLY:
			return KeyType.KEY_MULTIPLY;
		case Keyboard.KEY_N:
			return KeyType.KEY_N;
		case Keyboard.KEY_NEXT:
			return KeyType.KEY_NEXT;
		case Keyboard.KEY_NOCONVERT:
			return KeyType.KEY_NOCONVERT;
		case Keyboard.KEY_NUMLOCK:
			return KeyType.KEY_NUMLOCK;
		case Keyboard.KEY_NUMPAD0:
			return KeyType.KEY_NUMPAD0;
		case Keyboard.KEY_NUMPAD1:
			return KeyType.KEY_NUMPAD1;
		case Keyboard.KEY_NUMPAD2:
			return KeyType.KEY_NUMPAD2;
		case Keyboard.KEY_NUMPAD3:
			return KeyType.KEY_NUMPAD3;
		case Keyboard.KEY_NUMPAD4:
			return KeyType.KEY_NUMPAD4;
		case Keyboard.KEY_NUMPAD5:
			return KeyType.KEY_NUMPAD5;
		case Keyboard.KEY_NUMPAD6:
			return KeyType.KEY_NUMPAD6;
		case Keyboard.KEY_NUMPAD7:
			return KeyType.KEY_NUMPAD7;
		case Keyboard.KEY_NUMPAD8:
			return KeyType.KEY_NUMPAD8;
		case Keyboard.KEY_NUMPAD9:
			return KeyType.KEY_NUMPAD9;
		case Keyboard.KEY_NUMPADCOMMA:
			return KeyType.KEY_NUMPADCOMMA;
		case Keyboard.KEY_NUMPADENTER:
			return KeyType.KEY_NUMPADENTER;
		case Keyboard.KEY_NUMPADEQUALS:
			return KeyType.KEY_NUMPADEQUALS;
		case Keyboard.KEY_O:
			return KeyType.KEY_O;
		case Keyboard.KEY_P:
			return KeyType.KEY_P;
		case Keyboard.KEY_PAUSE:
			return KeyType.KEY_PAUSE;
		case Keyboard.KEY_PERIOD:
			return KeyType.KEY_PERIOD;
		case Keyboard.KEY_POWER:
			return KeyType.KEY_POWER;
		case Keyboard.KEY_PRIOR:
			return KeyType.KEY_PRIOR;
		case Keyboard.KEY_Q:
			return KeyType.KEY_Q;
		case Keyboard.KEY_R:
			return KeyType.KEY_R;
		case Keyboard.KEY_RBRACKET:
			return KeyType.KEY_RBRACKET;
		case Keyboard.KEY_RCONTROL:
			return KeyType.KEY_RCONTROL;
		case Keyboard.KEY_RETURN:
			return KeyType.KEY_RETURN;
		case Keyboard.KEY_RIGHT:
			return KeyType.KEY_RIGHT;
		case Keyboard.KEY_RMENU:
			return KeyType.KEY_RMENU;
		case Keyboard.KEY_RMETA:
			return KeyType.KEY_RMETA;
		case Keyboard.KEY_RSHIFT:
			return KeyType.KEY_RSHIFT;
		case Keyboard.KEY_S:
			return KeyType.KEY_S;
		case Keyboard.KEY_SCROLL:
			return KeyType.KEY_SCROLL;
		case Keyboard.KEY_SECTION:
			return KeyType.KEY_SECTION;
		case Keyboard.KEY_SEMICOLON:
			return KeyType.KEY_SEMICOLON;
		case Keyboard.KEY_SLASH:
			return KeyType.KEY_SLASH;
		case Keyboard.KEY_SLEEP:
			return KeyType.KEY_SLEEP;
		case Keyboard.KEY_SPACE:
			return KeyType.KEY_SPACE;
		case Keyboard.KEY_STOP:
			return KeyType.KEY_STOP;
		case Keyboard.KEY_SUBTRACT:
			return KeyType.KEY_SUBTRACT;
		case Keyboard.KEY_SYSRQ:
			return KeyType.KEY_SYSRQ;
		case Keyboard.KEY_T:
			return KeyType.KEY_T;
		case Keyboard.KEY_TAB:
			return KeyType.KEY_TAB;
		case Keyboard.KEY_U:
			return KeyType.KEY_U;
		case Keyboard.KEY_UNDERLINE:
			return KeyType.KEY_UNDERLINE;
		case Keyboard.KEY_UNLABELED:
			return KeyType.KEY_UNLABELED;
		case Keyboard.KEY_UP:
			return KeyType.KEY_UP;
		case Keyboard.KEY_V:
			return KeyType.KEY_V;
		case Keyboard.KEY_W:
			return KeyType.KEY_W;
		case Keyboard.KEY_X:
			return KeyType.KEY_X;
		case Keyboard.KEY_Y:
			return KeyType.KEY_Y;
		case Keyboard.KEY_YEN:
			return KeyType.KEY_YEN;
		case Keyboard.KEY_Z:
			return KeyType.KEY_Z;
		}
		return null;
	}
}
