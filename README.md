Network Address and Path Analysis for Elasticsearch
===================================================
[![Develop Travis Status](https://img.shields.io/travis/ofir123/elasticsearch-network-analysis/develop.svg)](https://travis-ci.org/ofir123/elasticsearch-network-analysis) 
[![GitHub release](https://img.shields.io/github/release/ofir123/elasticsearch-network-analysis.svg)](https://github.com/ofir123/elasticsearch-network-analysis/releases) 
[![Pull Requests](https://img.shields.io/github/issues-pr-raw/ofir123/elasticsearch-network-analysis.svg)](https://github.com/ofir123/elasticsearch-network-analysis/pulls)
[![Project Issues](https://img.shields.io/github/issues-raw/ofir123/elasticsearch-network-analysis.svg)](https://github.com/ofir123/elasticsearch-network-analysis/issues)
 
A set of network and path related analyzers, to better index and query network related data in Elasticsearch

Latest Elasticsearch version support: 6.2.2 
 
## Includes

- `network_address` analyzer - outputs network address (IPv4/MAC) parts. For example, it'd split `127.0.0.1` to `127`, `0`, `0` and `1`.
- `partial_network_address` analyzer - acts like the `network_address` analyzer, but will handle anything that looks like a part of a network address.  
For example, it'd split `127.0` to `127` and `0`; it'll also output `127` for `127` as an input. This analyzer is more useful for query analysis, than for actual documents.
- `strict_partial_network_address` analyzer - acts like the `partial` analyzer, but will handle anything that have at least two parts of a network address.
For example, it'd split `127.0` to `127` and `0`, but it will output *nothing* for `127` as an input.
- `full_network_address` analyzer - used to search for all the network addresses inside a given document.
- `path_keywords` analyzer. Splits path-like strings.  
- `incremental_capture_group` - A modified version of the `pattern_capture` token filter that also increments the tokens' position attribute and sets token offsets.

## Installation
- Download a release zip from the [releases page](https://github.com/ofir123/elasticsearch-network-analysis/releases) matching your ES version
- Run `bin/elasticsearch-plugin install file://<path to zip>` 
- Restart Elasticsearch 

## Usage

After installing, you may tell ES to use the analyzers listed above in mappings and queries.
To test the analyzers, the `_analyze` endpoint can be used:

    curl -XPOST http://<es_host>:9200/_analyze?pretty -d'
    {
        "analyzer": "network_address",
        "text": "AA:BB:CC:DD:EE:FF"
    }'
    
 Response:
     
     {
      "tokens":[
      {
        "token":"AA",
        ...
      },
      {
        "token":"BB",
        ...
      }
      ...
    }

## Examples

To test the plugin, the `_analyze` endpoint can be used:

	curl -XPOST http://localhost:9200/_analyze?pretty=1&analyzer=network_address -d '
	computer_name = "ABC"                                                   
	mac_address = AA:BB:CC:DD:EE:FF                                         
	SMBInfo for 192.168.0.1:                                               
		os = Windows Server (R) 2008 Enterprise 6002 Service Pack 2         
		lanman = Windows Server (R) 2008 Enterprise 6.0                     
		domain = WORKGROUP                                                  
		server_time = Tue Apr 24 10:35:57 2012                              
	smb_port = 445                                                          
	'

 response:

	{
	  "tokens":[
	  {
		"token":"AA",
		"start_offset":103,
		"end_offset":120,
		"type":"word",
		"position":2
	  },
	  {
		"token":"BB",
		...
	  }
	}

The next step is to update the mapping of your index to use the analyzer:

	mapping = {
		'[your_document_type]': {
			'properties': {
				...,
				'data': {
					"type": "multi_field",
					"fields": {
						# Normal string analysis. 
						"data": {
							'type': 'string',
							'term_vector': 'with_positions_offsets',
						},
						# Network address custom analysis.
						"network_address": {
							"type": "string",
							"analyzer": "network_address"
						}
					}
				},
				...
			}
		}
	}

	es.put_mapping('[your_index]', '[your_document_type]', mapping)

Then, documents can be queried using the new analyzed field:

	{
	  "query": {
		"match": {
		  "data.network_address": {
			"query": "AA:BB:CC:DD:EE:FF",
			# 'phrase' will only match exact address (not just parts of it)
			"type": "phrase"
		  }
		}
	  }
	}

The `partial_network_address` can be used at query time, to search for partial addresses (note that this only affects the *query* analysis, not the *document's*):

	{
	  "query": {
		"match": {
		  "data.network_address": {
			"query": "AA:BB:CC",
			"analyzer": "partial_network_address",
			"type": "phrase"
		  }
		}
	  }
	}
	
The `full_network_address` can be used as an analyzer, to search for all the network addresses inside a given document.

	network_addresses = self._es.indices.analyze(index=index_name, analyzer='full_network_address', body=document).get('tokens')

## Build

In the plugin folder:
- Run `mvn test` to run the tests.
- Run `mvn package` to build the jar.


For any more questions or issues, please [e-mail me](mailto:ofirbrukner@gmail.com).

Have fun :)
