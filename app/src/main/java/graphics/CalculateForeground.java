package graphics;

import android.graphics.Color;

public class CalculateForeground {
	//perceived luminance
	private static final int RED_LUMINANCE = 299;
	private static final int GREEN_LUMINANCE = 587;
	private static final int BLUE_LUMINANCE = 114;
	//calculated from http://en.wikipedia.org/wiki/Luminance(video)
	private static final int MAX_LUMINANCE = (RED_LUMINANCE * 255 + GREEN_LUMINANCE * 255 + BLUE_LUMINANCE * 255);
	private static final int MID_LUMINANCE = MAX_LUMINANCE / 2;

	/// <summary>

	/// Finds the foreground (white or black) that will be easiest to read
	/// with the given background
	/// </summary>

	public static int CalculateForeColor(Color backColor)
	{
	    int totalCustomBrightness = 
	        ((backColor.RED * RED_LUMINANCE) + (backColor.GREEN * GREEN_LUMINANCE) + (backColor.BLUE * BLUE_LUMINANCE));

	    if (totalCustomBrightness <= MID_LUMINANCE)
	        return Color.WHITE;
	    else
	        return Color.BLACK;
	}
}
