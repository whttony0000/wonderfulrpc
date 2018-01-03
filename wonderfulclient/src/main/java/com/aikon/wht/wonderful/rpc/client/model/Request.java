package com.aikon.wht.wonderful.rpc.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author haitao.wang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    String path;

    List<Header> headers;
}
