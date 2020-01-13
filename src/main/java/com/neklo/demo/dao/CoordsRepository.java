package com.neklo.demo.dao;

import com.neklo.demo.model.ObjectPosition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Repository
@Transactional
public class CoordsRepository {

	@Resource
	JdbcTemplate jdbcTemplate;

	public int addCords(ObjectPosition objectPosition) {
		return jdbcTemplate.update(
				"insert into test_coords.coords_data(longitude, latitude, object, moment)" +
						" values(?, ?, ?, ?) ON CONFLICT DO NOTHING ", objectPosition.getLongitude(), objectPosition.getLatitude(), objectPosition.getObject(),
				objectPosition.getTimestamp()
		);
	}
}
