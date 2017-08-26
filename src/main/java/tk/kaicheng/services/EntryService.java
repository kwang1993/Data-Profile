package tk.kaicheng.services;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.POJO.FeatureAndValue;
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/20.
 */

public interface EntryService {
    Entry save(Entry entry);

    void deleteAll();

    void delete(Integer id);

    void delete(Entry entry);

    List <Entry> findAll();

    Entry findOne(Integer id);

    Entry findByEntryNameAndProfile(String entryName, Profile profile);

    List<Entry> findByProfile(Profile profile);

    void updateEntryName(Integer entry_id, String newEntryName);

    void saveEntryFeature(Integer entry_id, Integer feature_id, String featureValue);

    void deleteEntryFeatureById(Integer entry_id, Integer feature_id);

    void updateEntryFeatureByFeatureValue(Integer entry_id, Integer feature_id, String newFeatureValue);

    String findEntryFeatureById(Integer entry_id, Integer feature_id);

    List<FeatureAndValue> findFeatureAndValuesByEntryId(Integer entry_id);

    FeatureAndValue findFeatureAndValueById(Integer entry_id, Integer feature_id);

    List<Object[]> findEntryFeatureByEntryId(Integer entry_id);

    List<Object[]> findAllEntryFeatures();
}
