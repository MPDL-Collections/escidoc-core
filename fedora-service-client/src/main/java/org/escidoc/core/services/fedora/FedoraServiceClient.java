package org.escidoc.core.services.fedora;

import net.sf.oval.constraint.NotEmpty;
import org.escidoc.core.services.fedora.access.ObjectDatastreamsTO;
import org.escidoc.core.services.fedora.access.ObjectProfileTO;
import org.escidoc.core.services.fedora.management.DatastreamProfileTO;
import org.esidoc.core.utils.VoidObject;
import org.esidoc.core.utils.io.MimeTypes;
import org.esidoc.core.utils.io.Stream;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * REST Client for Fedora repository.
 *
 * @author <a href="mailto:mail@eduard-hildebrandt.de">Eduard Hildebrandt</a>
 */
public interface FedoraServiceClient {

    PidListTO getNextPID(@NotNull @NotEmpty String namespace);

    PidListTO getNextPID(@NotNull @NotEmpty String namespace, int numPIDs);

    Future<PidListTO> getNextPIDAsync(@NotNull @NotEmpty String namespace);

    Future<PidListTO> getNextPIDAsync(@NotNull @NotEmpty String namespace, int numPIDs);

    void createObject(@NotNull CreateObjectPathParam path, @NotNull CreateObjectQueryParam query);

    Future<VoidObject> createObjectAsync(@NotNull CreateObjectPathParam path, @NotNull CreateObjectQueryParam query);

    ObjectProfileTO getObjectProfile(@NotNull @NotEmpty String pid);

    Future<ObjectProfileTO> getObjectProfileAsync(@NotNull @NotEmpty String pid);

    DigitalObjectTO getObjectXML(@NotNull @NotEmpty final String pid);

    Future<DigitalObjectTO> getObjectXMLAsync(@NotNull @NotEmpty final String pid);

    InputStream getObjectXMLAsStream(@NotNull @NotEmpty final String pid);

    Future<InputStream> getObjectXMLAsStreamAsync(@NotNull @NotEmpty final String pid);

    void updateObject(@NotNull UpdateObjectPathParam path, @NotNull UpdateObjectQueryParam query);

    Future<VoidObject> updateObjectAsync(@NotNull UpdateObjectPathParam path, @NotNull UpdateObjectQueryParam query);

    void deleteObject(@NotNull String pid);

    Future<VoidObject> deleteObjectAsync(@NotNull String pid);

    String ingest(@NotNull IngestPathParam path, @NotNull IngestQueryParam query,
                  @NotNull DigitalObjectTypeTOExtension digitalObjectTO);

    Future<String> ingestAsync(@NotNull IngestPathParam path, @NotNull IngestQueryParam query,
                               @NotNull DigitalObjectTypeTOExtension digitalObjectTO);

    DatastreamProfileTO addDatastream(@NotNull AddDatastreamPathParam path, @NotNull AddDatastreamQueryParam query,
                                      Stream outputStream);

    Future<DatastreamProfileTO> addDatastreamAsync(@NotNull AddDatastreamPathParam path,
                                                   @NotNull AddDatastreamQueryParam query, Stream outputStream);

    Stream getDatastream(@NotNull GetDatastreamPathParam path, @NotNull GetDatastreamQueryParam query);

    Future<Stream> getDatastreamAsync(@NotNull GetDatastreamPathParam path, @NotNull GetDatastreamQueryParam query);

    DatastreamProfileTO getDatastreamProfile(@NotNull GetDatastreamProfilePathParam path,
                                             @NotNull GetDatastreamProfileQueryParam query);

    Future<DatastreamProfileTO> getDatastreamProfileAsync(@NotNull GetDatastreamProfilePathParam path,
                                                          @NotNull GetDatastreamProfileQueryParam query);

    DatastreamProfileTO modifyDatastream(@NotNull ModifiyDatastreamPathParam path,
                                         @NotNull ModifyDatastreamQueryParam query, Stream outputStream);

    Future<DatastreamProfileTO> modifyDatastreamAsync(@NotNull ModifiyDatastreamPathParam path,
                                                      @NotNull ModifyDatastreamQueryParam query, Stream outputStream);

    void deleteDatastream(@NotNull DeleteDatastreamPathParam path, @NotNull DeleteDatastreamQueryParam query);

    Future<VoidObject> deleteDatastreamAsync(@NotNull DeleteDatastreamPathParam path,
                                             @NotNull DeleteDatastreamQueryParam query);

    DatastreamProfileTO setDatastreamState(@NotNull String pid, @NotNull @NotEmpty String dsID,
                                           @NotNull @NotEmpty DatastreamState state);

    Future<DatastreamProfileTO> setDatastreamStateAsync(@NotNull String pid, @NotNull @NotEmpty String dsID,
                                                        @NotNull @NotEmpty DatastreamState state);

    ObjectDatastreamsTO listDatastreams(@NotNull ListDatastreamsPathParam path,
                                        @NotNull ListDatastreamsQueryParam query);

    Future<ObjectDatastreamsTO> listDatastreamsAsync(@NotNull ListDatastreamsPathParam path,
                                                     @NotNull ListDatastreamsQueryParam query);
}
