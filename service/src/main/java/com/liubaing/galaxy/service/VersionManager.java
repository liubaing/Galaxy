package com.liubaing.galaxy.service;

import com.liubaing.galaxy.entity.Platform;
import com.liubaing.galaxy.entity.Version;
import com.liubaing.galaxy.repository.mongodb.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版本控制
 *
 * @author heshuai
 * @version 2012-10-26
 */
@Service
public class VersionManager {

    @Autowired
    private VersionRepository versionRepository;

    public Version checkUpdate() {
        return versionRepository.findByPlatformOrderByVersionCodeDesc(Platform.ANDROID);
    }

    public void saveUpdateRecord(Version version) {
        if (version != null) {
            versionRepository.save(version);
        }
    }

}
