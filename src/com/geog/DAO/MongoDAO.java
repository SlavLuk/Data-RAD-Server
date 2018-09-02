package com.geog.DAO;

import com.geog.Model.HeadState;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import java.util.*;
import javax.annotation.PreDestroy;
import org.bson.Document;


public class MongoDAO {
	
	// declare variables
	private List<HeadState> listHeads;
	private MongoClient mongoClient = null;
	private MongoDatabase database = null;	
	private HeadState user;

	public MongoDAO()throws Exception{
	
			
			mongoClient = new MongoClient();
			database= mongoClient.getDatabase("headsOfStateDB");
			
	}
	
	public void deleteHeadOfState(String id)throws Exception{
		
		//get collection and create document
		MongoCollection<Document> collection = database.getCollection("headsOfState");
		Document d = new Document();
		d.append("_id", id);
		//delete one document
		collection.deleteOne(d);
		
		
		
	}
	//add to collection one document
	public void addHeadOfState(String id,String head)throws Exception{
		
		MongoCollection<Document> collection = database.getCollection("headsOfState");
		Document d = new Document();
		d.append("_id", id.toUpperCase());
		d.append("headOfState", head);
		
		collection.insertOne(d);
		
	
	}
	
	public List<HeadState> getHeadOfSate()throws Exception{
		
		MongoCollection<Document> users2 = database.getCollection("headsOfState");
		FindIterable<Document> users = users2.find();
		Gson gson = new Gson();
		
		listHeads = new ArrayList<>();
		 
		 
		for (Document d : users) {
			
			
			   user = gson.fromJson(d.toJson(), HeadState.class);
			  
			   listHeads.add(user);
			}
		
		
		return listHeads;
	}
	

	@PreDestroy
	private void cleanUp(){
		
			mongoClient.close();	
		
	}
	

	
}
