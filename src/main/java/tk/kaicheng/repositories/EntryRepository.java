package tk.kaicheng.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/1.
 */
@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {
    Entry findByEntryNameAndProfile(String entryName, Profile profile);
    List <Entry> findByProfile(Profile profile);

    @Query(value = "update entry set entry_name = ?2 where entry_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateEntryName(Integer entry_id, String newEntryName);

    @Query(value = "insert into entry_feature(entry_id, feature_id, feature_value) values(?1, ?2, ?3)", nativeQuery = true)
    @Modifying
    @Transactional
    void saveEntryFeature(Integer entry_id, Integer feature_id, String featureValue);

    @Query(value = "delete from entry_feature where entry_id = ?1 and feature_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteEntryFeatureById(Integer entry_id, Integer feature_id);

    @Query(value = "update entry_feature set feature_value = ?3 where entry_id = ?1 and feature_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void updateEntryFeatureByFeatureValue(Integer entry_id, Integer feature_id, String newFeatureValue);

    @Query(value = "select feature_value from entry_feature where entry_id = ?1 and feature_id = ?2", nativeQuery = true)
    public String findEntryFeatureById(Integer entry_id, Integer feature_id);

    @Query(value = "select feature_id, feature_value from entry_feature where entry_id = ?1", nativeQuery = true)
    List<Object[]> findEntryFeatureByEntryId(Integer entry_id);

    @Query(value = "select ef.entry_id, f.feature_id, f.feature_name, ef.feature_value from feature f, entry_feature ef where ef.entry_id = ?1 and ef.feature_id = f.feature_id", nativeQuery = true)
    List<Object[]> findFeatureAndValuesByEntryId(Integer entry_id);

    @Query(value = "select ef.entry_id, f.feature_id, f.feature_name, ef.feature_value from feature f, entry_feature ef where ef.entry_id = ?1 and f.feature_id = ?2 and ef.feature_id = f.feature_id", nativeQuery = true)
    Object[] findFeatureAndValueById(Integer entry_id, Integer feature_id);

    @Query(value = "select entry_id, feature_id, feature_value from entry_feature", nativeQuery = true)
    List<Object[]> findAllEntryFeatures();

//    @Query(value = "select f.feature_name, ef.feature_value from entry_feature ef, feature f, entry p where ef.feature_id=f.feature_id and ef.entry_id=p.entry_id and p.entry_name=?1", nativeQuery = true)
//    @Modifying
//    List<Object[]> findFeatureNameAndFeatureValueByEntryName(String entryName);
}
