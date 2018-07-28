package org.jvalue.commons.mongodb.test;


import org.junit.Assert;
import org.junit.Test;
import org.jvalue.commons.EntityBase;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.ods.db.mongodb.repositories.MongoDocumentNotFoundException;

import java.util.List;

public abstract class AbstractRepositoryAdapterTest<T extends EntityBase> extends AbstractRepositoryTest {

	private GenericRepository<T> repository;


	@Override
	protected final void doCreateDatabase(DbConnectorFactory connectorFactory) {
		this.repository = doCreateRepository(connectorFactory);
	}


	@Test
	public final void testFindById() throws Exception {
		T value1 = doCreateValue("id1", "");
		T value2 = doCreateValue("id2", "");
		repository.add(value1);
		repository.add(value2);

		Assert.assertEquals(value1, repository.findById("id1"));
		Assert.assertEquals(value2, repository.findById("id2"));
	}


	@Test(expected = MongoDocumentNotFoundException.class)
	public void testInvalidId() {
		repository.findById("missingId");
	}


	@Test
	public void testGetAll() throws Exception {
		repository.add(doCreateValue("id1", ""));
		repository.add(doCreateValue("id2", ""));

		List<T> values = repository.getAll();
		Assert.assertEquals(2, values.size());
	}


	@Test(expected = MongoDocumentNotFoundException.class)
	public void testRemove() {
		repository.add(doCreateValue("id1", ""));
		repository.remove(repository.findById("id1"));
		try {
			repository.findById("id1");
		} catch (Exception e) {
			throw e;
		}
		Assert.fail();
	}


	@Test
	public void testUpdate() {
		repository.add(doCreateValue("id1", "hello"));
		T updatedValue = doCreateValue("id1", "world");
		repository.update(updatedValue);
		Assert.assertEquals(updatedValue, repository.findById("id1"));
	}


	protected abstract GenericRepository<T> doCreateRepository(DbConnectorFactory connectorFactory);

	protected abstract T doCreateValue(String id, String data);
}
