package com.example.domain;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MyWebServiceTest {
    @Inject private MyWebService mywebservice;
    
    @Deployment public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(MyWebService.class)
                .addClasses(User.class)
                .addClasses(Player.class)
                .addClasses(Link.class)
                .addClasses(TestUtils.class)
                				//Add the Gson Jar to the deployments with the following line:
                				.addAsLibrary(new File("lib/gson-2.1.jar"))
                                .addAsResource("META-INF/persistence.xml")
                                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setup() throws Exception{
    	//Put all the sample data into our database
    	
    	ArrayList<String> friendIDList = new ArrayList<String>();
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"newArray\"");
    	TestUtils.createPlayerWithFriends(1000, "Ihave%20ZeroFriends", friendIDList);
       	
    	friendIDList.clear();
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"67890\"");
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"One Friend\"");
    	TestUtils.createPlayerWithFriends(1001, "Ihave%20OneFriends", friendIDList);

    	friendIDList.clear();
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"67890\"");
    	friendIDList.add("\"76543\"");
    	friendIDList.add("\"89012\"");
    	friendIDList.add("\"21098\"");
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"One Friend\"");
		friendIDList.add("\"Two Friend\"");
		friendIDList.add("\"Three Friend\"");
		friendIDList.add("\"Four Friend\"");
    	TestUtils.createPlayerWithFriends(1004, "Ihave%20FourFriends", friendIDList);

    	friendIDList.clear();
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"67890\"");
    	friendIDList.add("\"76543\"");
    	friendIDList.add("\"89012\"");
    	friendIDList.add("\"21098\"");
    	friendIDList.add("\"91234\"");
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"One Friend\"");
		friendIDList.add("\"Two Friend\"");
		friendIDList.add("\"Three Friend\"");
		friendIDList.add("\"Four Friend\"");
		friendIDList.add("\"Five Friend\"");
    	TestUtils.createPlayerWithFriends(1005, "Ihave%20FiveFriends", friendIDList);

    	friendIDList.clear();
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"67890\"");
    	friendIDList.add("\"76543\"");
    	friendIDList.add("\"89012\"");
    	friendIDList.add("\"21098\"");
    	friendIDList.add("\"91234\"");
    	friendIDList.add("\"77441\"");
    	friendIDList.add("\"88552\"");
    	friendIDList.add("\"99663\"");
    	friendIDList.add("\"11223\"");
    	friendIDList.add("\"44556\"");
    	friendIDList.add("\"newArray\"");
    	friendIDList.add("\"One Friend\"");
    	friendIDList.add("\"Two Friend\"");
    	friendIDList.add("\"Three Friend\"");
    	friendIDList.add("\"Four Friend\"");
    	friendIDList.add("\"Five Friend\"");
    	friendIDList.add("\"Six Friend\"");
		friendIDList.add("\"Seven Friend\"");
		friendIDList.add("\"Eight Friend\"");
		friendIDList.add("\"Nine Friend\"");
		friendIDList.add("\"Ten Friend\"");
    	TestUtils.createPlayerWithFriends(1010, "Ihave%20TenFriends", friendIDList);
    }
    
    @After
    public void tearDown() throws Exception{
    	//Clear all the sample data out of our database now that we're done running all the tests.

    	String response = TestUtils.removePlayer(1000);
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\nRemove Player: " + response);
    	Assert.assertTrue(response.equals("Player removed with FacbookID: 1000"));
    	response = TestUtils.removePlayer(1001);
    	Assert.assertTrue(response.equals("Player removed with FacbookID: 1001"));
    	response = TestUtils.removePlayer(1004);
    	Assert.assertTrue(response.equals("Player removed with FacbookID: 1004"));
    	response = TestUtils.removePlayer(1005);
       	Assert.assertTrue(response.equals("Player removed with FacbookID: 1005"));
       	response = TestUtils.removePlayer(1010);
       	Assert.assertTrue(response.equals("Player removed with FacbookID: 1010"));
    }

    @Test
    public void testSetupData(){
	//Retrieve the sample data from our database and make sure it's what we expect
		
    	Player playerWith0Friends = TestUtils.getPlayer(1000);
    	Player playerWith1Friends = TestUtils.getPlayer(1001);
    	Player playerWith4Friends = TestUtils.getPlayer(1004);
    	Player playerWith5Friends = TestUtils.getPlayer(1005);
    	Player playerWith10Friends = TestUtils.getPlayer(1010);
    	
    	Assert.assertTrue(playerWith0Friends.getFriendList().size()==0);
    	Assert.assertTrue(playerWith1Friends.getFriendList().size()==1);
    	Assert.assertTrue(playerWith4Friends.getFriendList().size()==4);
    	Assert.assertTrue(playerWith5Friends.getFriendList().size()==5);
    	Assert.assertTrue(playerWith10Friends.getFriendList().size()==10);
    	
    	Assert.assertTrue(playerWith10Friends.getFriendList().get(0)==67890);
    	User twoFriend = TestUtils.getUser(playerWith5Friends.getFriendList().get(1));
    	Assert.assertTrue(twoFriend.getName().equals("Two Friend"));
    }
    
    @Test
    public void testIsDeployed() {
        Assert.assertNotNull(mywebservice);
    }
    
}