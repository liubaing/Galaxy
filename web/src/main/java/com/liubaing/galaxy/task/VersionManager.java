package com.liubaing.galaxy.task;

import com.liubaing.galaxy.entity.Platform;
import com.liubaing.galaxy.entity.Version;
import com.liubaing.galaxy.repository.mongodb.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版本控制
 * @author heshuai
 * @version 2012-10-26
 *
 */
@Service
public class VersionManager {

	@Autowired
	private VersionRepository versionRepository;
	
	public Version checkUpdate()
	{
		Version version = versionRepository.findByPlatformOrderByVersionCodeDesc(Platform.ANDROID);
        if(version != null){
            version.setUrl(Version.UPLOAD_VIRTUAL_PATH + version.getUrl());
        }
        return version;
    }
	
	public void saveUpdateRecord(Version version)
	{
		if (version != null ) {
			versionRepository.save(version);
		}
	}
	
}
