package com.projects.victor.messenger.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.projects.victor.messenger.database.DatabaseClass;
import com.projects.victor.messenger.model.Profile;

public class ProfileService {
	private Map<String, Profile> profiles = DatabaseClass.getProfiles();

	public ProfileService() {
		profiles.put("viclan", new Profile(1L,"viclan","Victor","Langat"));
		profiles.put("linn", new Profile(2L,"linn","Linner","Cherono"));
	}
	
	public List<Profile> getAllProfiles(){
		return new ArrayList<>(profiles.values());
	}
	
	public Profile getProfile(String profile) {
		return profiles.get(profile);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile){
		if(profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public void removeProfile(String profile) {
		profiles.remove(profile);
	}
}
