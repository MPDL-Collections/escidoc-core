This directory and the subdirectories contain the configuration for the eSciDoc-Core Search and Indexing.
NOTE: The index escidocoaipmh_all is used internally by the eSciDoc-Core-Infrastructure and may not get deleted!!

Indexing:
-Done internally by the eSciDoc-Core-Infrastructure

-Is triggered whenever an escidoc-object changes (create, update, delete).

-Needs one overall configuration-file named fedoragsearch.properties which is located in this directory.
    -In fedoragsearch.properties, state the names of the indexes that should be written 
     separated with whitespace as property with name fedoragsearch.indexNames.
    -For each index create a subdirectory ./index/<indexname> for the search + indexing configuration of this index.
    -Indexes that are not stated in the fedoragsearch.properties are not written, even if a configuration-directory exists.
    -Following properties can be set in fedoragsearch.properties:
        -fedoragsearch.indexNames: names of the indexes, whitespace-separated
        -fedoragsearch.pdfTextExtractorCommand:
         if pdfBox (internally used by gsearch to extract text from pdfs) is not working well for your pdfs,
		 use a command-line tool.
		 If you want to use a command-line tool,
		 define command-line-command to custom pdf-text-extractor (has to get installed seperately)
		 define command with full path, define inputfile with <inputfile> and outputfile with <outputfile>
		 example: C:/Programme/xpdf-3.02pl2-win32/pdftotext -cfg C:/Programme/xpdf-3.02pl2-win32/xpdfrc <inputfile> <outputfile>
        -fedoragsearch.ignoreTextExtractionErrors
         true|false Defines what happenes if an Exception occurs while extracting the text from an pdf for indexing
         if set to true, Exception is ignored and object is indexed without the fulltext.
         if set to false, Exception is thrown and object is not indexed at all.
        -fedoragsearch.cacheUrlResources
         true|false Defines if resources retrieved from an URL (eg indexing-stylesheet) are cached after first retrieve.
         If set to true, remember to flush cache when resource behind url is changed (operation=flushUrlResources)
        -fedoragsearch.soapBase
         points to the host where the fedoragsearch runs. 
         Change it if fedoragsearch runs on a different machine as eSciDocCore-Framework.

-For each index a subdirectory ./index/<indexname>, containing the configuration-files is needed

-Configuration-files for one index used by fedoragsearch are:
 (Please copy files from an existing index-configuration and modify)
    -indexInfo.xml, containing basic information about the index
    -index.properties, containing properties for the index
        -Properties are:
            -fgsindex.indexName: name of the index, according to name stated in fedoragsearch.properties
            -fgsindex.defaultUpdateIndexDocXslt: Name of the stylesheet used to 
             transform the escidoc-object-xml into the index-information-document. (see below)
             NOTE: stylesheet-file has to end with .xslt, property is name of the file without .xslt !!!
             Alternatively an URL to the stylesheet can be used. 
             Then you dont need to put a stylesheet in the config-directory.
            -fgsindex.indexDir: Directory where the index-files should get written.
             Specify absolute path.
            -fgsindex.analyzer: The Analyzer to use for indexing 
             (normally please use de.escidoc.sb.common.lucene.analyzer.EscidocAnalyzer)
              
            Try the following properties if you get OutOfMemoryExceptions while indexing.
            These Exceptions mostly occur when merging segments or optimizing the index.
            If setting these properties doesnt help, try setting "ulimit -v unlimited" on your machine.
            -fgsindex.mergeFactor: 
              The MergeFactor controls how many documents are stored within each segment 
              before a new one is started and how many are started before they are collected into a larger one. 
              So a Factor of 10 means, 10 documents before aggregating 
              and 10 aggregated indexes of a certain size before aggregating again. 
              Consequently MergeFactor controls the number of open files. 
            -fgsindex.maxBufferedDocs: 
              controls the number of documents to buffer in memory 
              before flushing to disk. 
              For a batch index operation the higher this is the higher the index performance 
              but the more memory will be consumed.
            -fgsindex.ramBufferSize:
              Define the maximum RAM Buffer size in MB.
              When this is set, the writer will flush whenever buffered documents and deletions use this much RAM. 
              Note that if flushing by document count is also enabled, 
              then the flush will be triggered by whichever comes first. 
            -fgsindex.maxMergeDocs: 
              limits the maximum number of documents within a segment above which merging does not happen. 
              This is used to limit the files size to avoid to high memory consumption when merging big files to one. 
            -fgsindex.maxMergeMb: limits the size of a segment above which merging does not happen. 
              This is used to limit the files size to avoid to high memory consumption when merging big files to one.
            -fgsindex.lucene.directory.implementation:
              Directory Implementation for lucene (used for merges + optimize)
              can be:
              
              org.apache.lucene.store.SimpleFSDirectory:
              is a straightforward implementation using java.io.RandomAccessFile. 
              However, it has poor concurrent performance (multiple threads will bottleneck) 
              as it synchronizes when multiple threads read from the same file.
              
              org.apache.lucene.store.NIOFSDirectory:
              uses java.nio's FileChannel's positional io when reading to avoid synchronization when reading from the same file. 
              Unfortunately, due to a Windows-only Sun JRE bug this is a poor choice for Windows, 
              but on all other platforms this is the preferred choice. 
              Applications using Thread.interrupt() or Future.cancel(boolean) should use SimpleFSDirectory instead. 
              See NIOFSDirectory java doc for details. 
              
              org.apache.lucene.store.MMapDirectory:
              uses memory-mapped IO when reading. This is a good choice if you have plenty of virtual memory relative to your index size, 
              eg if you are running on a 64 bit JRE, or you are running on a 32 bit JRE but your index sizes are 
              small enough to fit into the virtual memory space. Java has currently the limitation of not being able to unmap files from user code. 
              The files are unmapped, when GC releases the byte buffers. Due to this bug in Sun's JRE, MMapDirectory's IndexInput.close() 
              is unable to close the underlying OS file handle. Only when GC finally collects the underlying objects, 
              which could be quite some time later, will the file handle be closed. 
              This will consume additional transient disk usage: on Windows, attempts to delete or overwrite the files will result in 
              an exception; on other platforms, which typically have a "delete on last close" semantics, 
              while such operations will succeed, the bytes are still consuming space on disk. 
              For many applications this limitation is not a problem (e.g. if you have plenty of disk space, 
              and you don't rely on overwriting files on Windows) but it's still an important limitation to be aware of. 
              This class supplies a (possibly dangerous) workaround mentioned in the bug report, which may fail on non-Sun JVMs. 
              Applications using Thread.interrupt() or Future.cancel(boolean) should use SimpleFSDirectory instead. 
              See MMapDirectory java doc for details.
              By default Lucene detects the best implementation by itself.
            -fgsindex.maxChunkSize: 
              used by MMapDirectory class (Lucene Directory Implementation)
              Sets the maximum chunk size (default is Integer.MAX_VALUE for 64 bit JVMs and 256 MiBytes for 32 bit JVMs) 
              used for memory mapping. Especially on 32 bit platform, the address space can be very fragmented, 
              so large index files cannot be mapped. 
              Using a lower chunk size makes the directory implementation a little bit slower 
              (as the correct chunk must be resolved on each seek) 
              but the chance is higher that mmap does not fail (used by merges of segments).
    
    -index.object-types.properties:
        Holds properties to configure which types of eSciDoc-Objects should get indexed 
         or deleted from the index under which conditions:
         
            Example: If eSciDoc-items should get written into the index, 
            your properties for the item-indexing should start with the prefix Resource.Item.
            
            Object-Types that can get indexed are:
                -Item
                -Container
                -Context
                -OrganizationalUnit
                -ContentModel
                -ContentRelation
                
            For each Object-Type that shall get indexed in this index you can define 
            the following properties:                
            1. Define if indexing should be done synchronously or asynchronously
                Resource.<objecttype>.indexAsynchronous=true|false
            2. Optionally define a property Resource.<objecttype>.indexingPrerequisiteXpath
               if you only want to index the item if it meets some conditions.
               This property expects an XPath-Expression
            3. Optionally define a property Resource.<objecttype>.deletePrerequisiteXpath
               if you want to delete the object from the index when it meets some conditions.
               This property expects an XPath-Expression
            4. Optionally define a property Resource.<objecttype>.indexReleasedVersion=true or =both
               Set value to true:
               if you only want to index the last released version of an object.
               If this option is set, always the last released version of an object is indexed.
               If no last released version exists, nothing is indexed.
               Set value to both:
               if you want to index the last released version of an object additionally to the last version of an object.
               If this option is set, always the last released version and the last version of an object is indexed.
               If no last released version exists, only the last version is indexed.
               NOTE: if value is both you have to set the property cqlTranslator.filterLatestRelease in the srw-properties file to true!!
            5. Optionally for resource Item, you can define a property Resource.Item.indexFulltextVisibilities
               where you can define fulltexts of which visibility shall get indexed. (whitespace-separated)
               eg Resource.Item.indexFulltextVisibilities=private audience public
               This is just a variable with name INDEX_FULLTEXT_VISIBILITIES that gets passed to the indexing-stylesheet.
               So you have to handle fulltext-indexing dependent on this variable in your indexing-stylesheet.
   
            You can define properties for more than one objectType in one configuration-file.

    -eventually a stylesheet that is used by fedoragsearch 
     to transform the escidoc-object-xml into the index-information-document.
     For more doku about this stylesheet, please refer to the Documentation of the eSciDoc Search and Browse Component.
     

Search:
-Needs one overall configuration-file named SRWServer.props which is located in this directory.
    -In SRWServer.props, for each index that is searchable, state the search-interface to use
     and the path to the configuration-file. 
     
     Path to the configuration should be index/<indexname>/<config-file-name>
     so the index-configuration and the search-configuration files for one index are located in one Directory.
     
     Poperty-names must contain the name of the index, 
     eg db.escidoc_all.configuration, where escidoc_all is the indexname.
     
     The search-interface (property db.<indexname>.class) always must be de.escidoc.sb.srw.EscidocSRWDatabaseImpl !!

-For each index, needs a configuration-file with name + path as stated in the SRWServer.props.

-Configuration-file for one index must contain the following properties:
    -databaseInfo.title, databaseInfo.description, databaseInfo.contact:
     descriptive information about the database

    -cqlTranslator.indexPath:
     Path to the index: 
     Must match property fgsindex.indexDir defined in index.properties!

    -cqlTranslator.identifierTerm:
     Stored field in the index that is used for display.

    -numberOfRecords:
     default number of records to return per page. 

    -maximumRecords:
     highest possible maximum of records to retrun per page. 
     this overwrites the maximum that was given in request if it is above maximumRecords

    -cqlTranslator.defaultIndexField:
     default Index field to search if no field is provided in search. 

    -cqlTranslator.analyzer:
     Analyzer to use for search. 
     this must be the same Analyzer as used for indexing!

-Configuration-file for one index may contain the following properties:
    -cqlTranslator.forceScoring:
     Flag indicating if scoring has to get forced even for wildcard query or range query. 
     slows down the search if set to true

    -cqlTranslator.permissionFiltering:
     Flag indicating if query has to get expanded by a permission filterQuery
     because the index contains objects not everybody may see. 

    -cqlTranslator.filterLatestRelease:
     Flag indicating if index contains both latestReleased version and latestVersion of an Object.
     If so, we have to filter duplicates.
     this only works if database contains the following indexes for each document:
     -rootPid: Field containing the rootPid of the object, without version-identifier
     -type: Field containing the type (0: latestVersion, 1:latestRelease)
     NOTE: Also set the property indexReleasedVersion in the file index.object-types.properties
     to index both latestReleased version and latestVersion of an Object.

-Configuration-file for one index can contain configuration for a Highlighter:
    -cqlTranslator.highlighterClass
     must be set to de.escidoc.sb.srw.lucene.highlighting.EscidocHighlighter
     otherwise no highlighting is done

    -cqlTranslator.highlightXmlizerClass
     2 predefined Xmlizers available: 
     (if property is not set, de.escidoc.sb.srw.lucene.highlighting.EscidocSimpleHighlightXmlizer is used)
        -de.escidoc.sb.srw.lucene.highlighting.EscidocHighlightXmlizer
            -generates one xml-snippet per text-fragment with: 
             text to highlight + extra section containing positions of highlight-terms within the text.
        -de.escidoc.sb.srw.lucene.highlighting.EscidocSimpleHighlightXmlizer
            -generates one xml-snippet with text to highlight, 
             containing all text-fragments, including tags around terms to highlight (eg <B>)

    -cqlTranslator.highlightFragmentSize, default: 100
     length of one text-fragment containing terms to highlight

    -cqlTranslator.highlightMaxFragments, default: 4
     maximum number of text-fragments containing highlight-terms to display

    -cqlTranslator.highlightFragmentSeparator, default: <escidoc-fragment-separator>
     separator between 2 text-fragments

    -cqlTranslator.highlightStartMarker, default:<escidoc-highlight-start>
     used by EscidocSimpleHighlightXmlizer, tag that is set before start of term to highlight (eg <B>)

    -cqlTranslator.highlightEndMarker, default:<escidoc-highlight-end>
     used by EscidocSimpleHighlightXmlizer, tag that is set after end of term to highlight (eg </B>)

    -cqlTranslator.fulltextIndexField
     name of the fulltext index field.
     Used to determine if fulltext was searched and therefore fulltext has to get highlighted.

    -cqlTranslator.highlightTermFulltext
     name of the field containing the fulltext to highlight (field must be STORED and UN_TOKENIZED).

    -cqlTranslator.highlightTermFilename
     name of the field containing the name of the fulltext-file

    -cqlTranslator.highlightTermFulltextIterable (true or false, default is false)
     if more than one fulltext is indexed, you need one STORED,UN_TOKENIZED field containing the fulltext for each fulltext.
     if eg cqlTranslator.highlightTermFulltext is "fulltext" and cqlTranslator.highlightTermFulltextIterable is set to true,
     you need to have fields fulltext1 to fulltext<n> in your index, each containing text for one fulltext.
     if additionally cqlTranslator.highlightTermFilename is set to eg fulltext-file-name 
     and cqlTranslator.highlightTermFulltextIterable is set to true,
     you need to have fields fulltext-file-name1 to fulltext-file-name<n> in your index, each containing filename for one fulltext.
     If cqlTranslator.highlightTermFulltextIterable is set to false, you need to have 1 field with name defined in cqlTranslator.highlightTermFulltext
     containing the fulltext STORED, UN_TOKENIZED and if cqlTranslator.highlightTermFulltextIterable is set to false, one field with 
     name defined in cqlTranslator.highlightTermFilename, containing the filename of the fulltext.

    -cqlTranslator.highlightTermMetadata
     name of the field containing the metadata-text to highlight (field must be STORED and UN_TOKENIZED).

    
    
-Configuration-changes require a restart of the JBoss-Application-Server



Run the fedoragsearch.war and srw.war on a different server (in the following called search-server):
-both fedoragsearch.war and srw.war must run on the same search-server
-copy the directory containing the configuration in your ESCIDOC_HOME directory. (ie complete directory conf)
-make sure that ESCIDOC_HOME is set as environment variable.
-conf-directory has to contain: escidoc-core.properties, complete subdirectory "search".
-in the search config directory (default:conf/search/config), 
 you can remove the index.object-types.properties-files in the index directories
-on the core-framework server you have to keep the search-config-directory-structure and the index.object-types.properties-files,
 you can remove the rest.
 The escidoc-core.properties must contain the following properties:
    -search.properties.directory (relative to the config directory of ESCIDOC_HOME)
    -escidoc-core.selfurl (base-url of the escidoc-core framework)
    -gsearch.fedoraPass persistent handle of an system-inspector for access from fedoragsearch to eSciDoc-core framework.
 The escidoc-core.properties may contain:
    -escidoc-core.proxyHost
    -escidoc-core.proxyPort
    -escidoc-core.nonProxyHosts
-Adapt properties gsearch.url and srw.url in escidoc-core.custom.properties on core-framework server
    