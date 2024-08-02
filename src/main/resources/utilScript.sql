--calculate table count
SELECT table_name, COUNT(*) AS row_count
FROM information_schema.tables
WHERE table_schema = 'public' -- Replace 'public' with the schema name if needed
GROUP BY table_name
ORDER BY row_count DESC;