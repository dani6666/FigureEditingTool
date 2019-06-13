import java.awt.*;
/**
 * Interface from every type of Shape to implement.
 * Provides base methods to edit and gain information about the figures.
 * @author Daniel Włudarczyk
 */
interface IFigure
{
    /**
     * Resizes the figure by adding to its width and height the given growth.
     * Maintains the middle point of the figure.
     * @param growth Amount added to the size of the figure. Negtive means figure will decrease in size.
     */
    public void resize(float growth);
    /**
     * Moves a figure by given vector.
     * @param dx x coordinate of the vector
     * @param dy y coordinate of the vector
     */
    public void move(float dx, float dy);

    /**
     * Gives the x coordinate of the middle of the figure
     * @return The x coordinate of the middle of the figure
     */
    public float getCenterXFloat();
    /**
     * Gives the y coordinate of the middle of the figure
     * @return The y coordinate of the middle of the figure
     */
    public float getCenterYFloat();

    /**
     * Changes color of the figure
     * @param color Color to piant figure with
     */
    public void changeColor(Color color);

    /**
     * Determines if the given point is inside the figure.
     * @param x x coordinate of the point
     * @param y y coordinat of the point
     * @return True if point is inside the figure, false if not
     */
    public boolean isHit(float x, float y);

    /**
     * Returns the type of the figure (circle/rectangle/polygon).
     * @return The type of the figure as String
     */
    public String getType();
    /**
     * Returns the color of the figure.
     * @return The color of the figure
     */
    public Color getColor();
    /**
     * Returns a refference to {@link Shape} object.
     * @return A refferance to the {@link Shape} object
     */
    public Shape getShape();
}