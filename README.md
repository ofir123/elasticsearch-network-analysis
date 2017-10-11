Network Address and Path Analysis for Elasticsearch
=========================================
[![Develop Travis Status](https://img.shields.io/travis/matan129/elasticsearch-network-path-analysis/develop.svg)](https://travis-ci.org/matan129/elasticsearch-network-path-analysis) 
[![GitHub release](https://img.shields.io/github/release/matan129/elasticsearch-network-path-analysis.svg)](https://github.com/matan129/elasticsearch-network-path-analysis/releases) 
[![Pull Requests](https://img.shields.io/github/issues-pr-raw/matan129/elasticsearch-network-path-analysis.svg)](https://github.com/matan129/elasticsearch-network-path-analysis/pulls)
[![Project Issues](https://img.shields.io/github/issues-raw/matan129/elasticsearch-network-path-analysis.svg)](https://github.com/matan129/elasticsearch-network-path-analysis/issues)
 
 
## Includes

- `network_address` analyzer - outputs network address (IPv4/MAC) parts. For example, it'd split `127.0.0.1` to `127`, `0`, `0` and `1`.
- `partial_network_address` analyzer - acts like the previous analyzer, but will handle anything that looks like a part of a network address.
For example, it'd split `127.0` to `127` and `0`; it'll also output `127` for `127` as an input. This analyzer is most useful for query analysis, not actual documents.
- `strict_partial_network_address` analyzer - acts like the `partial` analyzer, but will handle anything that have at least two parts of a network address.
For example, it'd split `127.0` to `127` and `0`, but it will output *nothing* for `127` as an input.
- `full_network_address` analyzer - used to search for all the network addresses inside a given document.
- `path_keywords` analyzer. Splits path-like strings.  
- `incremental_capture_group` - A modified version of the `pattern_capture` token filter that also increments the tokens' position attribute and sets token offsets.

## Installation
- Download a release zip from the [releases page](https://github.com/matan129/elasticsearch-network-path-analysis/releases) matching your ES version
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
