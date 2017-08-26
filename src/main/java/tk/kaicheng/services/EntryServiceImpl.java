package tk.kaicheng.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.kaicheng.POJO.FeatureAndValue;
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;
import tk.kaicheng.repositories.EntryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry save(Entry entry){
        return entryRepository.save(entry);
    }

    @Override
    public void deleteAll(){
        entryRepository.deleteAll();
    }

    @Override
    public void delete(Integer id){
        entryRepository.delete(id);
    }

    @Override
    public void delete(Entry entry){
        entryRepository.delete(entry);
    }

    @Override
    public List <Entry> findAll(){
        return entryRepository.findAll();
    }

    @Override
    public Entry findOne(Integer id){
        return entryRepository.findOne(id);
    }

    @Override
    public Entry findByEntryNameAndProfile(String entryName, Profile profile){
        return entryRepository.findByEntryNameAndProfile(entryName, profile);
    }

    @Override
    public List<Entry> findByProfile(Profile profile) {
        return entryRepository.findByProfile(profile);
    }

    @Override
    public void updateEntryName(Integer entry_id, String newEntryName) {
        entryRepository.updateEntryName(entry_id, newEntryName);
    }

    @Override
    public void saveEntryFeature(Integer entry_id, Integer feature_id, String featureValue) {
        entryRepository.saveEntryFeature(entry_id, feature_id, featureValue);
    }

    @Override
    public void deleteEntryFeatureById(Integer entry_id, Integer feature_id) {
        entryRepository.deleteEntryFeatureById(entry_id, feature_id);
    }

    @Override
    public void updateEntryFeatureByFeatureValue(Integer entry_id, Integer feature_id, String newFeatureValue) {
        entryRepository.updateEntryFeatureByFeatureValue(entry_id, feature_id, newFeatureValue);
    }

    @Override
    public String findEntryFeatureById(Integer entry_id, Integer feature_id) {
        return entryRepository.findEntryFeatureById(entry_id, feature_id);
    }

    @Override
    public List<FeatureAndValue> findFeatureAndValuesByEntryId(Integer entry_id) {
        List<Object[]> ls = entryRepository.findFeatureAndValuesByEntryId(entry_id);
        List <FeatureAndValue> res = new ArrayList<>();
        if(ls == null || ls.size() == 0 ) return res;
        for(Object [] objects : ls ){
            Integer entryId = Integer.valueOf((String) objects[0]);
            Integer featureId = Integer.valueOf((String) objects[1]);
            String featureName = (String)objects[2];
            String featureValue = (String)objects[3];
            FeatureAndValue featureAndValue = new FeatureAndValue(entryId, featureId, featureName, featureValue);            res.add(featureAndValue);
        }
        return res;
    }

    @Override
    public FeatureAndValue findFeatureAndValueById(Integer entry_id, Integer feature_id) {
        Object[] objects = entryRepository.findFeatureAndValueById(entry_id, feature_id);
        Integer entryId = Integer.valueOf((String) objects[0]);
        Integer featureId = Integer.valueOf((String) objects[1]);
        String featureName = (String)objects[2];
        String featureValue = (String)objects[3];
        FeatureAndValue featureAndValue = new FeatureAndValue(entryId, featureId, featureName, featureValue);
        return featureAndValue;
    }

    @Override
    public List<Object[]> findEntryFeatureByEntryId(Integer entry_id) {
        return entryRepository.findEntryFeatureByEntryId(entry_id);
    }

    @Override
    public List<Object[]> findAllEntryFeatures() {
        return entryRepository.findAllEntryFeatures();
    }

}
