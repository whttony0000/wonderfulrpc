package com.aikon.wht.wonderful.rpc.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author haitao.wang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest extends Request{

    Object entity;

}
