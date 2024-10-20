package com.hysea.watch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Component;

@Component
public class MyWatcher implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
        Event.KeeperState state = watchedEvent.getState();
        Event.EventType type = watchedEvent.getType();
        System.out.println("检测到节点变化...");
        System.out.println("节点名称" + state.name());
        System.out.println("事件类型" + type.name());
        System.out.println("节点路径" + watchedEvent.getPath());
    }
}
