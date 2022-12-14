package ca.uqam.inf6150.ete2021.gr010;

import ca.uqam.inf6150.ete2021.gr010.flight.db.model.City;
import ca.uqam.inf6150.ete2021.gr010.flight.db.model.Flight;
import ca.uqam.inf6150.ete2021.gr010.flight.gui.math.Degree;
import ca.uqam.inf6150.ete2021.gr010.flight.gui.math.LibGDXMathUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class FlightHandler
        implements Disposable {

    private static final String PLANE_TEXTURE_PATH = "assets/plane.png";
    private static final float  PLANE_SIZE         = 24f;
    private static final float  PLANE_SPEED        = 100f;
    private static final float  AIRPORT_RADIUS     = 5f;

    private final Texture m_planeTexture;

    private Sprite m_plane;
    private Circle m_arrivalAirport;
    private Circle m_departureAirport;

    private Vector2 m_planeDir;

    public FlightHandler() {
        m_planeTexture = new Texture(PLANE_TEXTURE_PATH);

        m_plane = new Sprite(m_planeTexture);
        m_plane.setSize(PLANE_SIZE, PLANE_SIZE);
        m_plane.setOriginCenter();
    }

    public void findAirportCoordinates(Flight flight, Vector2 halfViewport) {
        City beginAirport = flight.getBeginAirport().getCity();
        City endAirport   = flight.getEndAirport().getCity();

        spawnAirports(halfViewport, beginAirport, endAirport);
    }

    private void spawnAirports(final Vector2 halfViewport, final City beginAirport, final City endAirport) {
        m_arrivalAirport   = new Circle();
        m_departureAirport = new Circle();

        m_departureAirport.x = halfViewport.x + (float) ((beginAirport.getLongitude() / Degree.HALF.getDegrees() * halfViewport.x));
        m_departureAirport.y = halfViewport.y + (float) ((beginAirport.getLatitude() / Degree.QUARTER.getDegrees() * halfViewport.y));
        m_arrivalAirport.x   = halfViewport.x + (float) ((endAirport.getLongitude() / Degree.HALF.getDegrees() * halfViewport.x));
        m_arrivalAirport.y   = halfViewport.y + (float) ((endAirport.getLatitude() / Degree.QUARTER.getDegrees() * halfViewport.y));

        m_arrivalAirport.radius   = AIRPORT_RADIUS;
        m_departureAirport.radius = AIRPORT_RADIUS;

        Vector2 start = new Vector2(m_departureAirport.x, m_departureAirport.y);
        Vector2 end   = new Vector2(m_arrivalAirport.x, m_arrivalAirport.y);

        m_planeDir = end.sub(start).nor();
    }

    public void setFlightOrientation() {
        m_plane.setCenter(m_departureAirport.x, m_departureAirport.y);
        m_plane.flip(false, m_arrivalAirport.x < m_departureAirport.x);
        m_plane.rotate(computeFlightRotation());
    }

    private float computeFlightRotation() {
        final Vector2 departurePosition = LibGDXMathUtils.getCirclePosition(m_departureAirport);
        final Vector2 arrivalPosition   = LibGDXMathUtils.getCirclePosition(m_arrivalAirport);

        Vector2 relativePosition = new Vector2(arrivalPosition).sub(departurePosition);

        return relativePosition.angleDeg();
    }

    public void destroyFlight() {
        m_plane            = null;
        m_arrivalAirport   = null;
        m_departureAirport = null;
        m_planeDir.setZero();
    }

    @Override
    public void dispose() {
        m_planeTexture.dispose();
    }

    public void drawPlane(Batch m_batch) {
        if (planeExist()) {
            m_plane.draw(m_batch);
        }
    }

    public void drawAirports(ShapeRenderer m_shapeRenderer) {
        if (airportExist()) {
            m_shapeRenderer.circle(m_departureAirport.x, m_departureAirport.y, AIRPORT_RADIUS);
            m_shapeRenderer.circle(m_arrivalAirport.x, m_arrivalAirport.y, AIRPORT_RADIUS);
        }
    }

    public boolean planeExist() {
        final boolean planeExist = m_plane != null;
        assert planeExist == airportExist();

        return planeExist;
    }

    public boolean airportExist() {
        return m_arrivalAirport != null && m_departureAirport != null;
    }

    public boolean planeOverlapsAirport() {
        return Intersector.overlaps(m_arrivalAirport, m_plane.getBoundingRectangle());
    }

    public void translatePlane() {
        Vector2 translation = new Vector2(m_planeDir).scl(PLANE_SPEED).scl(Gdx.graphics.getDeltaTime());
        m_plane.translate(translation.x, translation.y);
    }
}
