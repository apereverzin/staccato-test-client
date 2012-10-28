package com.creek.staccato.client.addressee;

import java.util.HashSet;
import java.util.Set;

import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class AddresseeProducer {
    private Set<GroupKey> groupKeys;
    private Set<ProfileKey> profileKeys;
    
    public AddresseeProducer(String emailAddress) {
        ProfileKey founderKey = new ProfileKey(emailAddress);
        Profile founder = new Profile(founderKey);

        ProfileKey profileKey = new ProfileKey(emailAddress);
        Profile profile = new Profile(profileKey);
        
        GroupKey groupKey = new GroupKey("group1", founderKey);
        Group group = new Group(groupKey);
        group.getProfileKeys().add(profileKey);
        groupKeys = new HashSet<GroupKey>();
        groupKeys.add(groupKey);
        
        profileKeys = new HashSet<ProfileKey>();
        profileKeys.add(profileKey);
    }
    
    public Set<GroupKey> getGroupKeys() {
        return groupKeys;
    }

    public Set<ProfileKey> getProfileKeys() {
        return profileKeys;
    }
}
