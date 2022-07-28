/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hujian.mall.authcenter.jackson2;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.hujian.mall.authcenter.domain.MemberDetail;
import com.hujian.mall.mapper.ums.entity.UmsAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Set;

/**
 * Custom Deserializer for {@link MemberDetail} class. This is already registered with
 * {@link MemberUserMixin}. You can also use it directly with your mixin class.
 *
 * @author Jitendra Singh
 * @since 4.2
 * @see MemberUserMixin
 */
class MemberUserDeserializer extends JsonDeserializer<MemberDetail> {

	private static final TypeReference<Set<SimpleGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<Set<SimpleGrantedAuthority>>() {
	};

	/**
	 * This method will create {@link User} object. It will ensure successful object
	 * creation even if password key is null in serialized json, because credentials may
	 * be removed from the {@link User} by invoking {@link User#eraseCredentials()}. In
	 * that case there won't be any password key in serialized json.
	 * @param jp the JsonParser
	 * @param ctxt the DeserializationContext
	 * @return the user
	 * @throws IOException if a exception during IO occurs
	 * @throws JsonProcessingException if an error during JSON processing occurs
	 */
	@Override
	public MemberDetail deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);
		Set<? extends GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"),
				SIMPLE_GRANTED_AUTHORITY_SET);
		JsonNode passwordNode = readJsonNode(jsonNode, "password");
		String username = readJsonNode(jsonNode, "username").asText();
		String password = passwordNode.asText("");
		boolean enabled = readJsonNode(jsonNode, "enabled").asBoolean();
		boolean accountNonExpired = readJsonNode(jsonNode, "accountNonExpired").asBoolean();
		boolean credentialsNonExpired = readJsonNode(jsonNode, "credentialsNonExpired").asBoolean();
		boolean accountNonLocked = readJsonNode(jsonNode, "accountNonLocked").asBoolean();

//		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode umsAdminJsonNode = readJsonNode(jsonNode, "umsAdmin");
		UmsAdmin admin = JSONUtil.toBean(umsAdminJsonNode.toPrettyString(), UmsAdmin.class);
		MemberDetail result = new MemberDetail(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
				authorities,admin);
		if (passwordNode.asText(null) == null) {
			result.eraseCredentials();
		}
		return result;
	}

	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
		return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
	}

}
