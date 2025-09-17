# NNFL (National Nuclear Forensics Library)
This repository contains a Spring Boot application that manages a nuclear-forensics material library, bootstrapping as a standard Spring service entry point. 
Domain records capture detailed measurements and contextual information (general info, geology, isotopes, processing data, containers, etc.) across defined stages of the nuclear fuel lifecycle.
The service layer persists those records in MongoDB, re-indexes them on changes, and exposes both structured filtering and natural-language search pathways. 
Natural-language requests are translated into MongoDB filters through an OpenAI-powered converter tailored to the collection schema, enabling expressive querying without manual JSON. 
Complementing that, materials are embedded with LangChain/OpenAI models and stored in a MongoDB vector index to support similarity search and record matching workflows.
A Spring MVC layer drives the web experience, letting users issue vector, natural-language, or raw-filter searches from the UI and view matching materials. 
Secure APIs handle account creation, password setup, and sign-in, with JWT-based protection enforced by the security configuration. 
File attachments are supported through pluggable storage backends (local filesystem or Amazon S3), making the platform adaptable to different deployment environments.