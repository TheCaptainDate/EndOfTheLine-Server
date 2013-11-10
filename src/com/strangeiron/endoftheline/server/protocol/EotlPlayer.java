/**
 * Strange Iron indie-gamedev studio
 * @descr: Данный класс реализует удаленного игрока:
 * Содержит все данные и параметры, а так же является уникальным индификатором игрока.
 * @author: Maksim Fomin (Date)
 */
package com.strangeiron.endoftheline.server.protocol;

import com.strangeiron.endoftheline.server.entity.EotlCharacter;

public class EotlPlayer {
	public EotlPlayerConnection connection;
	public String Name;
        public boolean[] buttons;
        public EotlCharacter character;
}
