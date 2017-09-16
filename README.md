Network Address and Path Analysis for Elasticsearch
=========================================
[![Develop Travis Status](https://img.shields.io/travis/matan129/elasticsearch-network-analysis/develop.svg)](https://travis-ci.org/matan129/elasticsearch-network-analysis) 
[![GitHub release](https://img.shields.io/github/release/matan129/elasticsearch-network-analysis.svg)](https://github.com/matan129/elasticsearch-network-analysis/releases) 
[![Pull Requests](https://img.shields.io/github/issues-pr-raw/matan129/elasticsearch-network-analysis.svg)](https://github.com/matan129/elasticsearch-network-analysis/pulls)
[![Project Issues](https://img.shields.io/github/issues-raw/matan129/elasticsearch-network-analysis.svg)](https://github.com/matan129/elasticsearch-network-analysis/issues)
 
 
## Includes

- `network_address` analyzer - outputs network address (IPv4/MAC) parts. For example, it'd split `127.0.0.1` to `127`, `0`, `0` and `1`.
- `partial_network_address` analyzer - acts like the previous analyzer, but will handle anything that looks like a part of a network address.
For example, it'd split `127.0` to `127` and `0`; it'll also output `127` for `127` as an input. This analyzer is most useful for query analysis, not actual documents.
- `full_network_address` analyzer - used to search for all the network addresses inside a given document.
- `incremental_capture_group_token_filter`
- `path_keywords` analyzer. Splits path-like strings.  

## Installation
- Download a release zip from the [releases page](https://github.com/matan129/elasticsearch-network-analysis/releases) matching your ES version
- Run `bin/elasticsearch-plugin install file://<path to zip>` 
- Restart Elasticsearch 

## Usage

After installing, you may tell ES to use the analyzers listed above in mappings and queries.
To test the analyzers, the `_analyze` endpoint can be used. For instance

    curl -XPOST http://<es_host>:9200/_analyze?&analyzer=network_address -d'
    {
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
    }
