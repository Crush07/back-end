package com.hysea;

import org.springframework.beans.factory.parsing.*;

public class HyseaListener implements ReaderEventListener {

    @Override
    public void defaultsRegistered(DefaultsDefinition defaultsDefinition) {

    }

    @Override
    public void componentRegistered(ComponentDefinition componentDefinition) {

    }

    @Override
    public void aliasRegistered(AliasDefinition aliasDefinition) {
        System.out.println(aliasDefinition);
    }

    @Override
    public void importProcessed(ImportDefinition importDefinition) {

    }
}
