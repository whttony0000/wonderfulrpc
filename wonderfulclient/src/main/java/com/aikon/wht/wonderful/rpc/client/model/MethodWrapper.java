package com.aikon.wht.wonderful.rpc.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * @author haitao.wang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MethodWrapper {


    Object[] args;

    Class[] argTypes;
}
