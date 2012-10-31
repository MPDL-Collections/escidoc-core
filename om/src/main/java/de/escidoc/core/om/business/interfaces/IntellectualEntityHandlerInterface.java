package de.escidoc.core.om.business.interfaces;

import java.util.List;

import de.escidoc.core.common.exceptions.EscidocException;

public interface IntellectualEntityHandlerInterface {
    String getIntellectualEntity(String id) throws EscidocException;

    String updateIntellectualEntity(String xml) throws EscidocException;

    String getLifeCyclestatus(String id) throws EscidocException;

    String getIntellectuakEntitySet(List<String> ids) throws EscidocException;

    String getIntellectualEntityVersionSet(String id) throws EscidocException;

    String ingestIntellectualEntity(String xml) throws EscidocException;
}