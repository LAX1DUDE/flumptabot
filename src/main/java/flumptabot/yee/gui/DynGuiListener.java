package flumptabot.yee.gui;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

public interface DynGuiListener {

	public MessageEmbed rebuild();
	
	/**
	 * e e e e e e e e e e e e e e e e e e e e e e<br />
	 * ============================================<br />
	 * <b>return true if GUI should reconstruct!</b><br />
	 * ==========================================<br />
	 * e e e e e e e e e e e e e e e e e e e e e e<br />
	 */
	public boolean onClick(int index, Message message, DynGui guiObject);

}
