import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import com.lavasoft.GeoIPService;

public class GeoIpServiceTests {
  @Test
  public void testMyIp() {
    String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("188.162.86.3");
    assertEquals(geoIP, "<GeoIP><Country>RU</Country><State>54</State></GeoIP>");
  }

  @Test(enabled = true)
  public void testInvalidIp() {
    String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("188.162.86.x");
    assertEquals(geoIP, "<GeoIP><Country>RU</Country><State>54</State></GeoIP>");
  }
}
