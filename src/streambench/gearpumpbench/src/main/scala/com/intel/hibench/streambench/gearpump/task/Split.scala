/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intel.hibench.streambench.gearpump.task

import com.intel.hibench.streambench.gearpump.util.GearpumpConfig
import org.apache.gearpump.Message
import org.apache.gearpump.cluster.UserConfig
import org.apache.gearpump.streaming.task.{Task, TaskContext}

class Split(taskContext: TaskContext, conf: UserConfig) extends Task(taskContext, conf) {
  private val benchConf = conf.getValue[GearpumpConfig](GearpumpConfig.BENCH_CONFIG).get
  private val separator = benchConf.separator
  import taskContext.output

  override def onNext(msg: Message): Unit = {
    msg.msg.asInstanceOf[String].split(separator).filter(_.nonEmpty).foreach { msg =>
      output(new Message(msg, System.currentTimeMillis()))
    }
  }
}