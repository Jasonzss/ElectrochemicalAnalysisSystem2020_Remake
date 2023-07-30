package com.bluedot.resource;

import com.bluedot.TestUtil;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Jason
 * @since 2023/07/30 - 22:16
 */
public class PermissionResourceTest extends BaseJerseyTest{
    final String path = "permissions";

    @Test
    public void testUpdatePermission(){
        final Form form = new Form();
        form.param("permission-id", "10000001");
        form.param("permission-name", "user:get:*");
        form.param("description", "查询任意用户信息");

        Response post = target(path).request().put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        TestUtil.printResponse(post);
    }
}
